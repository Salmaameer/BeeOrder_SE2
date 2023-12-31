package com.beeorder.orders.service.notification;

import java.util.PriorityQueue;

import org.springframework.stereotype.Component;

@Component
public class notificationQueue {
    PriorityQueue<notification> notifications = new PriorityQueue<>();
}
