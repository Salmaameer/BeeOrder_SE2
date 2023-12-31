package com.beeorder.orders.service.OrdersManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beeorder.orders.service.account.Account;
import com.beeorder.orders.service.notification.NotificationQueue;
import com.beeorder.orders.service.notification.PlacedNotification;
import com.beeorder.orders.service.order.Order;
import com.beeorder.orders.service.order.OrderStatus;
import com.beeorder.orders.service.order.OrdersInventory;
import com.beeorder.orders.service.order.PairDto;
import com.beeorder.orders.service.order.SimpleOrder;
import com.beeorder.orders.service.product.Product;
import com.beeorder.orders.service.product.ProductRepo;
import com.beeorder.orders.service.product.ProductService;


@Service
public class CompoundOrderManager {
     @Autowired
    NotificationQueue queue ;

    
    public CompoundOrderManager  (NotificationQueue q){
        this.queue = q;
    }

    public String createCompoundOrder(ProductService productService, List<PairDto> orderComp, List<Account> authorizedAccounts, OrdersInventory ordersInventory) {
        ProductRepo productRepo = productService.repos;
        // generate compound order to add simple orders to it
        Order newOrder = new Order();
        int randomId = generateID();
        newOrder.orderComponents = new ArrayList<>();

        // the products that order contains
        List<Product> allProducts = new ArrayList<>();

        // function to fill the products list
        allProducts = productsList(productRepo, orderComp);

        newOrder = assignProductsToOrders(allProducts, authorizedAccounts);
        newOrder.setId(randomId);
        ordersInventory.orders.add(newOrder);

        return "Order has been created successfully with ID " + newOrder.getId();
    }

    // used for assigning products to orders in compound order
    // as if it is a set of simple orders stored all in the compound order
    public Order assignProductsToOrders(List<Product> ourProducts, List<Account> accounts) {
        int noAcc = accounts.size();
        int productNo = ourProducts.size();
        int noProductsPerorder = productNo / noAcc;
        int num = productNo % noAcc;
        PlacedNotification placementNotification = new PlacedNotification();
        // make the compound order that will contains the simple orders
        Order compoundOrder = new Order();

        int i = 0, accCounter = 0;
        // while loop to divide the products between all accounts
        while (i < productNo) {
            SimpleOrder simpleOrder = new SimpleOrder();

            // to handel the remaining of the products
            while (num != 0) {
                simpleOrder.getOrderProduct().add(ourProducts.get(i));
                // calculate product total depends on quantity
                double productTotal = ourProducts.get(i).getPrice() * ourProducts.get(i).getQuantity();
                simpleOrder.setTotalCost(simpleOrder.getTotalCost() + productTotal);
                i++;
                num--;
            }
            // this loop the desired products to the simple order
            for (int j = i; j < i + noProductsPerorder; j++) {
                simpleOrder.getOrderProduct().add(ourProducts.get(j));
                simpleOrder.setTotalCost(simpleOrder.getTotalCost() + ourProducts.get(i).getPrice());
            }

            if (isEnoughBalance(accounts.get(accCounter), simpleOrder.getTotalCost())) {
                // sending notification for every simple order found in the compound order
                
                simpleOrder.setId(generateID());
                simpleOrder.setOrderAccount(accounts.get(accCounter));
                placementNotification.sendNotification(simpleOrder);
                simpleOrder.setStatus(OrderStatus.PLACED);
                compoundOrder.getOrderComponents().add(simpleOrder);
            }

            accCounter++;
            i += noProductsPerorder;
        }
        return compoundOrder;
    }

    public boolean isEnoughBalance(Account acc, double totalAmount) {
        return totalAmount + 50 < acc.getBalance();
    }

    public int generateID() {
        Random rand = new Random();
        int randomInt = rand.nextInt(10000);
        return randomInt;

    }

    public List<Product> productsList(ProductRepo productRepo, List<PairDto> orderComp) {
        List<Product> allProducts = new ArrayList<>();
        for (PairDto pair : orderComp) {
            for (Product prod : productRepo.products) {
                if (pair.getName().equals(prod.getName())) {
                    if (prod.getQuantity() >= pair.getQuantity()) {

                        // set the same found product but with asked user quantity
                        int q = prod.getQuantity(); // save old quantity
                        Product userPro = prod;
                        userPro.setQuantity(pair.getQuantity());
                        allProducts.add(userPro);
                        //update the quantity in the inventory
                        prod.setQuantity(q);
                       
                        prod.setQuantity(q - pair.getQuantity()); // update the quantity
                    }
                }
            }
        }
        return allProducts;
    }
}
