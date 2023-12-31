package com.beeorder.orders.service.order;
import com.beeorder.orders.service.account.Account;
import com.beeorder.orders.service.product.Product;

import ch.qos.logback.core.status.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// class representing simple order or leaf in the composite pattern used
// where it only contains one order and cannot have other orders, only products
@Getter @Setter
public class SimpleOrder extends orderComponent {
    private int id;
    private double totalCost;
    public List<Product> orderProduct = new ArrayList<>();
    public Account orderAccount;
    public LocalTime creationTime; 
    public OrderStatus status;

    // default constructor setting the creation time of this product. 
    public SimpleOrder(){
        creationTime = LocalTime.now();
        Random rand = new Random();
        int randomInt = rand.nextInt(10000);
        setId(randomInt);
    }

    public orderComponent viewDetails() {
        for (Product o : orderProduct) {
            return o.viewDetails();
        }
        return null;
    }

    public void deductFromBalance()
    {
        double currBalance = orderAccount.getBalance();
        currBalance -= ( totalCost + 50 ); // order cost+ shippment fees
        orderAccount.setBalance(currBalance);
        System.out.println(orderAccount.getBalance());
        notification.sendNotification(this);
    }
}
