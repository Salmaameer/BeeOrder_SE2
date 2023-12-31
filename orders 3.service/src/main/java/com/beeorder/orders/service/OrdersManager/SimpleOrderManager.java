package com.beeorder.orders.service.OrdersManager;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.beeorder.orders.service.account.Account;
import com.beeorder.orders.service.account.AccountRepo;
import com.beeorder.orders.service.account.AccountService;
import com.beeorder.orders.service.notification.PlacedNotification;
import com.beeorder.orders.service.order.OrdersInventory;
import com.beeorder.orders.service.order.PairDto;
import com.beeorder.orders.service.order.SimpleOrder;
import com.beeorder.orders.service.product.Product;
import com.beeorder.orders.service.product.ProductRepo;
import com.beeorder.orders.service.product.ProductService;

@Service
public class SimpleOrderManager {
    private List<Account> authorizedAccounts;
    OrdersInventory ordersInventory; 

    public SimpleOrderManager(OrdersInventory ordersInv){
        this.ordersInventory = ordersInv;
    }

    public String makeSimpleProduct(ProductService productService, List<PairDto> orderComp){
        ProductRepo productRepo = productService.repos;
        PlacedNotification placementNotification  = new PlacedNotification();
        SimpleOrder newOrder = new SimpleOrder();
        // int randomId = generateID();
        // newOrder.setId(randomId);
        newOrder.orderProduct = new ArrayList<>();

        // the products that order contains
        List<Product> allProducts = new ArrayList<>();

        // function to fill the products list
        allProducts = productsList(productRepo, orderComp);

        // update the total cost
        for (Product p : allProducts){
            newOrder.setTotalCost(newOrder.getTotalCost() + p.getPrice());
        }
        newOrder.setOrderProduct(allProducts);
        newOrder.setOrderAccount(authorizedAccounts.get(0));
        ordersInventory.orders.add(newOrder);
        placementNotification.sendNotification(newOrder);
        return "Order has been created successfully with ID "+ newOrder.getId() + " with total cost = "+ newOrder.getTotalCost()+ " + 50 for shippment";
    }


    public List<Product> productsList(ProductRepo productRepo ,List<PairDto> orderComp ){
        List<Product> allProducts = new ArrayList<>();
        for (PairDto pair : orderComp) {
            for (Product prod : productRepo.products) {
                if (pair.getName().equals(prod.getName())) {
                    if (prod.getQuantity() >= pair.getQuantity()) {

                        // set the same found product but with asked user  quantity
                        Product userPro = prod;
                        userPro.setQuantity(pair.getQuantity());
                        allProducts.add(userPro);
                        //update the quantity in the inventory
                        prod.setQuantity(prod.getQuantity() - pair.getQuantity()); // update the quantity
                    }
                } else {
                    System.out.println("Can't add this item to the order");
                }
            }
        }

        return allProducts;
    }

    public String authorizeUsers(AccountService accService, List<String> userNames) {
        List<Account> authorizedAccounts = new ArrayList<>();
        List<String> temp = new ArrayList<String>(userNames);

        AccountRepo accRepo = accService.accountRepo;
        //authorize by checking if the username exists in the accounts repo
        for (Account acc : accRepo.accounts) {
            for (String name : userNames) {
                System.out.println(name);
                if (name.equals(acc.getUserName())) {
                    authorizedAccounts.add(acc);
                    temp.remove(name);
                }
            }
        }
        this.authorizedAccounts = authorizedAccounts;

        String feedBack = "";
        if (temp.isEmpty())
            return ("All users Found");
        else {
            for (String n : temp) {
                feedBack += n;
                feedBack += " ";
            }
            feedBack += "Not found";
        }
        return feedBack;
    }
    
}
