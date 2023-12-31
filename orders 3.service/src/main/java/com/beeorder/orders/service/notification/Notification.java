package com.beeorder.orders.service.notification;

import com.beeorder.orders.service.order.Order;
import com.beeorder.orders.service.order.SimpleOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Setter @Getter
public abstract class Notification {
    String language;
    int id;
    String channel;
    SimpleOrder myOrder;
    HashMap<String, SimpleOrder> placeHolders = new HashMap<>();

    public abstract void  createNotificationContent();

    
}
