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
    
    OrdersInventory ordersInventory = new OrdersInventory(); // 
    private List<Account> authorizedAccounts = new ArrayList<>();; // contains the accounts that exist in the system , to authorize to create the user
    SimpleOrderManager simpleOrderManager = new SimpleOrderManager();
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
        return simpleOrderManager.createSimpleOrder(productService, orderComp, ordersInventory,authorizedAccounts);
    }

    public String makeCompoundOrder(ProductService productService, List<PairDto> orderComp) {
        return compoundOrderManager.createCompoundOrder(productService, orderComp, authorizedAccounts, ordersInventory);
    }

    public String cancelOrder(int id, ProductService prService){
        for (orderComponent order : ordersInventory.orders) {
            if(order.getId() == id){
                if(order instanceof SimpleOrder){
                    SimpleOrder temp = (SimpleOrder) order;
                    if(temp.getStatus() == OrderStatus.SHIPPED){
                        simpleOrderManager.cancelProcess(temp, prService, ordersInventory);
                    }
                        
                    }
                    else{
                        Order temp = (Order) order;
                        List<SimpleOrder> coSimpleOrders = temp.orderComponents;
                        for(SimpleOrder simOrder : coSimpleOrders){
                            simpleOrderManager.cancelProcess(simOrder,prService,ordersInventory);
;                        }
                        
                    }
                }

        }
        return "Cancellation has been done successfully";
    }


    // public void cancelProcess(SimpleOrder order,  ProductService prService){

    //     LocalTime currentTime =  LocalTime.now();
    //     Duration duration = Duration.between(order.getCreationTime() , currentTime);
    //     Long minutes = duration.toMinutes() % 60;
    //     if ( minutes <= 1) {
    //                         /*
    //                         cancellation process
    //                         1- return deducted money to the account
    //                         2- remove the order from the repo
    //                         3- remove from notification queue
    //                         4- return the quantity of the products
    //                         5- give feedback
    //                          */
    //         double accBal = order.getOrderAccount().getBalance();
    //         // shipment fees will not be returned ;)
    //         order.getOrderAccount().setBalance(accBal + order.getTotalCost());


    //         List<Product> orderProducts = order.getOrderProduct();

    //         List<Product> inventroyPro = prService.repos.products;

    //         // return the previous quantity of the products
    //         for (Product product : order.getOrderProduct()){
    //             for (Product invenPro : inventroyPro){
    //                 if (product.getId() == invenPro.getId()){
    //                     int currentQuan = invenPro.getQuantity();
    //                     //return the previous quantity of the inventory product
    //                     invenPro.setQuantity(currentQuan + product.getQuantity());
    //                 }
    //             }

    //         }
    //         // change order status
    //         for ( orderComponent o : ordersInventory.orders){
    //             if (o.getId() == order.getId()){
    //                 order.setStatus(OrderStatus.CANCELLED);
    //                 o = order;
    //                 break;
    //             }
    //         }








    //     }



    //     }



    }



