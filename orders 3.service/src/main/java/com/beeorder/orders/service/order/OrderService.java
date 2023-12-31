package com.beeorder.orders.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.beeorder.orders.service.account.Account;
import com.beeorder.orders.service.account.AccountRepo;
import com.beeorder.orders.service.account.AccountService;
import com.beeorder.orders.service.product.*;
import ch.qos.logback.core.joran.sanity.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// represents services provided by the order module
@Service
public class OrderService{
    OrdersInventory ordersInventory; // 
    private List<Account> authorizedAccounts; // contains the accounts that exist in the system , to authorize to create the user

    public OrderService(OrdersInventory o) {
        this.ordersInventory = o;
    }


//    public void addComponent(int id, SimpleOrder comp) {
//
//        for (Order o : ordersInventory.orders) {
//            if (o.getId() == id) {
//                o.orderComponents.add(comp);
//            }
//
//        }
//    }
//
//
//    public String removeComponent(int id, SimpleOrder comp) {
//
//        for (Order o : ordersInventory.orders) {
//            if (o.getId() == id) {
//                o.orderComponents.remove(comp);
//                return ("Product deleted");
//            }
//        }
//
//        return ("This product doesn't exist");
//
//    }


    public boolean isExist(int id) {
        return true;
    }

    // this function authorize the accounts that orders will be assigned to it
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


    public String makeSimple(ProductService productService, List<PairDto> orderComp){
        ProductRepo productRepo = productService.repos;

        SimpleOrder newOrder = new SimpleOrder();
        int randomId = generateID();
        newOrder.setId(randomId);
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

        return "Order has been created successfully with ID "+ newOrder.getId() + " with total cost = "+ newOrder.getTotalCost()+ " + 50 for shippment";
    }
public String makeCompoundOrder(ProductService productService, List<PairDto> orderComp) {
    ProductRepo productRepo = productService.repos;


    // generate compound order to add simple orders to it
    Order newOrder = new Order();
    int randomId = generateID();
    System.out.println(newOrder.getId());

    newOrder.orderComponents = new ArrayList<>();

    // the products that order contains
    List<Product> allProducts = new ArrayList<>();

    // function to fill the products list
    allProducts = productsList(productRepo, orderComp);

    newOrder = assignProductsToOrders(allProducts ,this.authorizedAccounts);
    newOrder.setId(randomId);
    ordersInventory.orders.add(newOrder);
    return "Order has been created successfully with ID "+ newOrder.getId() ;

}
//used for assigning products to orders in compound order
// as if it is a set of simple orders stored all in the compound order
public Order assignProductsToOrders(List<Product> ourProducts , List<Account> accounts){
    int noAcc = accounts.size();
    int productNo = ourProducts.size();
    int noProductsPerorder = productNo / noAcc;
    int num = productNo % noAcc;

    // make the compound order that will contains the simple orders
    Order compoundOrder = new Order();

    int i = 0 , accCounter = 0;
    // while loop to divide the products between all accounts
    while (i < productNo) {
        SimpleOrder simpleOrder = new SimpleOrder();

        // to handel the remaining of the products
        while (num != 0) {
            simpleOrder.getOrderProduct().add(ourProducts.get(i));
            simpleOrder.setTotalCost(simpleOrder.getTotalCost() + ourProducts.get(i).getPrice());
            i++;
            num--;
        }
        // this loop the desired products to the simple order
        for (int j = i; j < i + noProductsPerorder; j++) {
            simpleOrder.getOrderProduct().add(ourProducts.get(j));
            simpleOrder.setTotalCost(simpleOrder.getTotalCost() + ourProducts.get(i).getPrice());
        }

        if ( isEnoughBalance(accounts.get(accCounter), simpleOrder.getTotalCost())){
            simpleOrder.setId(generateID());
            simpleOrder.setOrderAccount(accounts.get(accCounter));
            compoundOrder.getOrderComponents().add(simpleOrder);

        }
        accCounter++;
        i += noProductsPerorder;
    }


    return compoundOrder;
}





    public  boolean isEnoughBalance(Account acc , double totalAmount){
        return totalAmount+50 < acc.getBalance();
    }
    public int generateID(){
        Random rand = new Random();
        int randomInt = rand.nextInt(10000);
        return randomInt;

    }
    public List<Product> productsList(ProductRepo productRepo ,List<PairDto> orderComp ){

        List<Product> allProducts = new ArrayList<>();
        for (PairDto pair : orderComp) {
            for (Product prod : productRepo.products) {
                if (pair.getName().equals(prod.getName())) {
                    if (prod.getQuantity() >= pair.getQuantity()) {
                        allProducts.add(prod);
                        prod.setQuantity(prod.getQuantity() - pair.getQuantity()); // update the quantity
                    }
                } else {
                    System.out.println("Can't add this item to the order");
                }
            }
        }

        return allProducts;
    }



}
