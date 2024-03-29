package com.beeorder.orders.service.order;

import java.util.ArrayList;
import java.util.List;

import com.beeorder.orders.service.account.Account;
import com.beeorder.orders.service.notification.NotificationQueue;
import com.beeorder.orders.service.notification.ShipmentNotification;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Order extends orderComponent{
    private int id;
    public List<SimpleOrder> orderComponents = new ArrayList<>();
    ShipmentNotification notification ;


    
    public orderComponent viewDetails() {
        for(SimpleOrder o : orderComponents)
        {
            return o.viewDetails();
        }
        return null;
    }

    public void addComponent(SimpleOrder orderComp){
        orderComponents.add(orderComp);
    }

    public void deductFromBalance(NotificationQueue notifyQueue)
    {
        notification = new ShipmentNotification(notifyQueue);
        for(SimpleOrder order : orderComponents)
        {
            double currBalance = order.getOrderAccount().getBalance();
            currBalance -= ( order.getTotalCost() + 50 ); // order cost+ shippment fees
            order.getOrderAccount().setBalance(currBalance);
            System.out.println(order.getOrderAccount().getBalance());
            notification.sendNotification(order);
            order.setStatus(OrderStatus.SHIPPED);
            System.out.println("from corder de : " + notifyQueue.placementNotifications.size());

        }
    }

   
}
