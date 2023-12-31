
package com.beeorder.orders.service.product;

import com.beeorder.orders.service.notification.NotificationQueue;
import com.beeorder.orders.service.order.orderComponent;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Product extends orderComponent {
    private String name;
    private double price;
    private int quantity;
    private String category;
    private String vendor;
    @Override
    public orderComponent viewDetails() {
        return this;
    }
    @Override
    public void deductFromBalance(NotificationQueue q) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deductFromBalance'");
    }
}
