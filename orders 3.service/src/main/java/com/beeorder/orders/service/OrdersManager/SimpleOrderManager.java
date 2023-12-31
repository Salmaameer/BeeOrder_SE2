package com.beeorder.orders.service.OrdersManager;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beeorder.orders.service.account.Account;
import com.beeorder.orders.service.account.AccountRepo;
import com.beeorder.orders.service.account.AccountService;
import com.beeorder.orders.service.notification.Notification;
import com.beeorder.orders.service.notification.NotificationQueue;
import com.beeorder.orders.service.notification.PlacedNotification;
import com.beeorder.orders.service.order.OrderStatus;
import com.beeorder.orders.service.order.OrdersInventory;
import com.beeorder.orders.service.order.PairDto;
import com.beeorder.orders.service.order.SimpleOrder;
import com.beeorder.orders.service.order.orderComponent;
import com.beeorder.orders.service.product.Product;
import com.beeorder.orders.service.product.ProductRepo;
import com.beeorder.orders.service.product.ProductService;
 

@Service
public class SimpleOrderManager {
    @Autowired
    NotificationQueue queue ;

    
    public SimpleOrderManager  (NotificationQueue q){
        this.queue = q;
    }
    

    public String createSimpleOrder(ProductService productService, List<PairDto> orderComp,OrdersInventory ordersInv,List<Account> authorized){
        ProductRepo productRepo = productService.repos;
        PlacedNotification placementNotification  = new PlacedNotification();
        
        SimpleOrder newOrder = new SimpleOrder();
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
        System.out.println(newOrder.getOrderProduct());
        newOrder.setOrderAccount(authorized.get(0));
        System.out.println(newOrder.getOrderAccount());
        ordersInv.orders.add(newOrder);
        placementNotification.sendNotification(newOrder);
        newOrder.setStatus(OrderStatus.PLACED);
        return "Order has been created successfully with ID "+ newOrder.getId() + " with total cost = "+ newOrder.getTotalCost()+ " + 50 for shippment";
    }

    public List<Product> productsList(ProductRepo productRepo ,List<PairDto> orderComp ){
        List<Product> allProducts = new ArrayList<>();
        for (PairDto pair : orderComp) {
            for (Product prod : productRepo.products) {
        
                
                if (pair.getName().equals(prod.getName())) {
                    if (prod.getQuantity() >= pair.getQuantity()) {

                        // set the same found product but with asked user  quantity
                         int q = prod.getQuantity(); // save old quantity
                        Product userPro = prod;
                        userPro.setQuantity(pair.getQuantity());
                        allProducts.add(userPro);
                        //update the quantity in the inventory
                        prod.setQuantity(q);
                       
                        prod.setQuantity(q - pair.getQuantity()); // update the quantity
                       
                    }
                } else {
                    System.out.println("Can't add this item to the order");
                }
            }
        }

        return allProducts;
    }

    public String cancelProcess(SimpleOrder order,  ProductService prService , OrdersInventory ordersInventory){
        LocalTime currentTime =  LocalTime.now();
        Duration duration = Duration.between(order.getCreationTime() , currentTime);
        Long minutes = duration.toMinutes() % 60;
        if (minutes <= 1) {
            /*
            cancellation process
            1- return deducted money to the account DONE
            2- change the status of the order to CANCELED DONE
            3- remove from notification queue
            4- return the quantity of the products DONE
            5- give feedback
            */
            double accBal = order.getOrderAccount().getBalance();
        
            // shipment fees will not be returned ;)
            order.getOrderAccount().setBalance(accBal + order.getTotalCost()); // return the deducted money to the account.


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

            // remove this order' notification from notification queue.
            // 1- check the status of the order
                // -> if it placed, go to placementNotifications queue.
                // -> if it shipped, go to shippingNotification queue.
            // 2- search for the notification (with id) and remove it

            System.out.println("order stat : " + order.status);
            System.out.println("queue size " + queue.shipmentNotifications.size() );
            if(order.getStatus() == OrderStatus.PLACED){
                for (Notification notification : queue.placementNotifications) {
                    if(notification.getId() == order.getId()){
                        System.out.println(notification.getMessage() + "has been deleted");
                        queue.placementNotifications.remove(notification);
                        break;
                    }
                }
            }else if(order.getStatus() == OrderStatus.SHIPPED){
                for (Notification notification : queue.shipmentNotifications) {
                    if(notification.getId() == order.getId()){
                        System.out.println(notification.getMessage() + "has been deleted");
                        queue.shipmentNotifications.remove(notification);
                        break;
                    }
                }
            }
            return "Order has been canceled!";
        }else{
            System.out.println(minutes);
            return "You can not cancel the order!";
        }
        // return "";
    }
}

