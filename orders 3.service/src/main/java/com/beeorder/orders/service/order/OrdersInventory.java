package com.beeorder.orders.service.order;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrdersInventory {
    List<SimpleOrder> orders = new ArrayList<SimpleOrder>();

}
