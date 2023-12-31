package com.beeorder.orders.service.notification;

import com.beeorder.orders.service.order.Order;
import com.beeorder.orders.service.order.SimpleOrder;
import com.beeorder.orders.service.order.orderComponent;

import lombok.Getter;
import lombok.Setter;


@Setter @Getter
public abstract class Notification {
    int id;
    String language;
    String channel;
    String message;

    public abstract void sendNotification(SimpleOrder order);
}
