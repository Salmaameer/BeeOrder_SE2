package com.beeorder.orders.service.order;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Order extends orderComponent  {

    public List<orderComponent> orderComponents;

    @Override
    public orderComponent viewDetails() {
        for(orderComponent o : orderComponents)
        {
            return o.viewDetails();
        }
        return null;
    }
}
