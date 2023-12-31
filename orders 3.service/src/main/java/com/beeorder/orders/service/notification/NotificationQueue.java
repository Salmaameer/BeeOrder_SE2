package com.beeorder.orders.service.notification;

import java.util.PriorityQueue;

import org.springframework.stereotype.Component;

@Component
public class NotificationQueue {
    PriorityQueue<Notification> notifications = new PriorityQueue<>();
}
