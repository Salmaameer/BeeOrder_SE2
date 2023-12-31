package com.beeorder.orders.service.order;

import java.util.List;

import com.beeorder.orders.service.notification.NotificationQueue;
import com.beeorder.orders.service.notification.ShipmentNotification;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public abstract class orderComponent
{
    int id;
    public abstract orderComponent viewDetails();
    public abstract void deductFromBalance(NotificationQueue notifyQueue);

}
