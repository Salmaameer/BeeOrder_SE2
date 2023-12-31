package com.beeorder.orders.service.order;

import java.time.LocalTime;
import java.util.ArrayList;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import com.beeorder.orders.service.OrdersManager.CompoundOrderManager;
import com.beeorder.orders.service.OrdersManager.SimpleOrderManager;
import com.beeorder.orders.service.account.Account;
import com.beeorder.orders.service.account.AccountRepo;
import com.beeorder.orders.service.account.AccountService;
import com.beeorder.orders.service.notification.PlacedNotification;
import com.beeorder.orders.service.product.*;
import ch.qos.logback.core.joran.sanity.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// represents services provided by the order module
@Service
public class OrderService{
    OrdersInventory ordersInventory; // 
    private List<Account> authorizedAccounts; // contains the accounts that exist in the system , to authorize to create the user

    SimpleOrderManager simpleOrderManager = new SimpleOrderManager(ordersInventory);
    CompoundOrderManager compoundOrderManager = new CompoundOrderManager();

    public OrderService(OrdersInventory o) {
        this.ordersInventory = o;
    }


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
        return simpleOrderManager.makeSimpleProduct(productService, orderComp);
    }

    public String makeCompoundOrder(ProductService productService, List<PairDto> orderComp) {
        return compoundOrderManager.createCompoundOrder(productService, orderComp, authorizedAccounts, ordersInventory);
//     ProductRepo productRepo = productService.repos;
//     // generate compound order to add simple orders to it
//     Order newOrder = new Order();
//     int randomId = generateID();
//     System.out.println(newOrder.getId());

//     newOrder.orderComponents = new ArrayList<>();

//     // the products that order contains
//     List<Product> allProducts = new ArrayList<>();

//     // function to fill the products list
//     allProducts = productsList(productRepo, orderComp);

//     newOrder = assignProductsToOrders(allProducts ,this.authorizedAccounts);
//     newOrder.setId(randomId);
//     ordersInventory.orders.add(newOrder);

//     return "Order has been created successfully with ID "+ newOrder.getId() ;

    }
//used for assigning products to orders in compound order
// as if it is a set of simple orders stored all in the compound order
// public Order assignProductsToOrders(List<Product> ourProducts , List<Account> accounts){
//     int noAcc = accounts.size();
//     int productNo = ourProducts.size();
//     int noProductsPerorder = productNo / noAcc;
//     int num = productNo % noAcc;
//     PlacedNotification placementNotification = new PlacedNotification();
//     // make the compound order that will contains the simple orders
//     Order compoundOrder = new Order();

//     int i = 0 , accCounter = 0;
//     // while loop to divide the products between all accounts
//     while (i < productNo) {
//         SimpleOrder simpleOrder = new SimpleOrder();

//         // to handel the remaining of the products
//         while (num != 0) {
//             simpleOrder.getOrderProduct().add(ourProducts.get(i));
//             //calculate product total depends on quantity
//             double productTotal = ourProducts.get(i).getPrice() * ourProducts.get(i).getQuantity();
//             simpleOrder.setTotalCost(simpleOrder.getTotalCost() + productTotal );
//             i++;
//             num--;
//         }
//         // this loop the desired products to the simple order
//         for (int j = i; j < i + noProductsPerorder; j++) {
//             simpleOrder.getOrderProduct().add(ourProducts.get(j));
//             simpleOrder.setTotalCost(simpleOrder.getTotalCost() + ourProducts.get(i).getPrice());
//         }

//         if ( isEnoughBalance(accounts.get(accCounter), simpleOrder.getTotalCost())){
//             //sending notification for every simple order found in the compound order
//             placementNotification.sendNotification(simpleOrder);
//             simpleOrder.setId(generateID());
//             simpleOrder.setOrderAccount(accounts.get(accCounter));
//             compoundOrder.getOrderComponents().add(simpleOrder);
//         }

//         accCounter++;
//         i += noProductsPerorder;
//     }
//     return compoundOrder;
// }

//     public  boolean isEnoughBalance(Account acc , double totalAmount){
//         return totalAmount+50 < acc.getBalance();
//     }

//     public int generateID(){
//         Random rand = new Random();
//         int randomInt = rand.nextInt(10000);
//         return randomInt;

//     }

    // public List<Product> productsList(ProductRepo productRepo ,List<PairDto> orderComp ){

    //     List<Product> allProducts = new ArrayList<>();
    //     for (PairDto pair : orderComp) {
    //         for (Product prod : productRepo.products) {
    //             if (pair.getName().equals(prod.getName())) {
    //                 if (prod.getQuantity() >= pair.getQuantity()) {

    //                     // set the same found product but with asked user  quantity
    //                     Product userPro = prod;
    //                     userPro.setQuantity(pair.getQuantity());
    //                     allProducts.add(userPro);
    //                     //update the quantity in the inventory
    //                     prod.setQuantity(prod.getQuantity() - pair.getQuantity()); // update the quantity
    //                 }
    //             } else {
    //                 System.out.println("Can't add this item to the order");
    //             }
    //         }
    //     }

    //     return allProducts;
    // }

    public String cancelOrder(int id, ProductService prService){
        for (orderComponent order : ordersInventory.orders) {
            if(order.getId() == id){
                if(order instanceof SimpleOrder){
                    SimpleOrder temp = (SimpleOrder) order;
                    if(temp.getStatus() == OrderStatus.SHIPPED){


                        }


                        return "Sorry the order has been shipped!";
                    }
                    else{
                        
                    }
                }

        }
        return "";
    }


    public void cancelProcess(SimpleOrder order,  ProductService prService){

        LocalTime currentTime =  LocalTime.now();
        Duration duration = Duration.between(order.getCreationTime() , currentTime);
        Long minutes = duration.toMinutes() % 60;
        if ( minutes <= 1) {
                            /*
                            cancellation process
                            1- return deducted money to the account
                            2- remove the order from the repo
                            3- remove from notification queue
                            4- return the quantity of the products
                            5- give feedback
                             */
            double accBal = order.getOrderAccount().getBalance();
            // shipment fees will not be returned ;)
            order.getOrderAccount().setBalance(accBal + order.getTotalCost());


            List<Product> orderProducts = order.getOrderProduct();

            List<Product> inventroyPro = prService.repos.products;

            // return the previous quantity of the products
            for (Product product : order.getOrderProduct()){
                for (Product invenPro : inventroyPro){
                    if (product.getId() == invenPro.getId()){
                        int currentQuan = invenPro.getQuantity();
                        //return the previous quantity of the inventory product
                        invenPro.setQuantity(currentQuan + product.getQuantity());
                    }
                }

            }
            // change order status
            for ( orderComponent o : ordersInventory.orders){
                if (o.getId() == order.getId()){
                    order.setStatus(OrderStatus.CANCELLED);
                    o = order;
                    break;
                }
            }








        }



        }



    }



