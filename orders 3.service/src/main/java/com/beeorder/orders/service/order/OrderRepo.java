package com.beeorder.orders.service.order;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
public class OrderRepo {
    private int orderId;
    List<orderComponent> orderComponents = new ArrayList<orderComponent>();
}
