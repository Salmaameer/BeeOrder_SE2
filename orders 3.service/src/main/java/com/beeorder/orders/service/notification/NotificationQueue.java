package com.beeorder.orders.service.notification;

import java.util.PriorityQueue;

import org.springframework.stereotype.Component;

@Component
public class NotificationQueue {
    public PriorityQueue<Notification> placementNotifications = new PriorityQueue<>();
    public PriorityQueue<Notification> shipmentNotifications = new PriorityQueue<>();
}
