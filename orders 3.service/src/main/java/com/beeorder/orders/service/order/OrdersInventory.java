package com.beeorder.orders.service.order;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter @Setter
public class OrdersInventory {
    public List<orderComponent> orders = new ArrayList<orderComponent>();

}
