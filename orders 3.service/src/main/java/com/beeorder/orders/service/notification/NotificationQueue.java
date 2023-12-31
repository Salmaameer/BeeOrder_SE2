package com.beeorder.orders.service.notification;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Component;

@Component
public class NotificationQueue {
    public Queue<Notification> placementNotifications = new LinkedList<>();
    public Queue<Notification> shipmentNotifications = new LinkedList<>();
}
