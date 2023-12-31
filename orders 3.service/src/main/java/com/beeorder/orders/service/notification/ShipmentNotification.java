package com.beeorder.orders.service.notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beeorder.orders.service.order.OrderStatus;
import com.beeorder.orders.service.order.SimpleOrder;
import com.beeorder.orders.service.product.Product;

@Service
public class ShipmentNotification extends Notification{
    @Autowired
    NotificationQueue queue = new NotificationQueue();
    
    
    @Override
    public void sendNotification(SimpleOrder order) {
        message = "Dear %s your order which contains %s has been shipped successfully.";
        String name;
        String products = " ";

        name = order.getOrderAccount().getUserName();
        for (Product p : order.orderProduct) {
            products += p.getName();
            products += " "; 
        }

        String formattedMsg = String.format(message, name, products);
        System.out.println(formattedMsg);
        this.setMessage(formattedMsg);
        this.setId(order.getId());
        this.setLanguage(order.getOrderAccount().getLanguage());
        this.setChannel(order.getOrderAccount().getNotificationChannel());
        queue.shipmentNotifications.add(this);
    }
}
