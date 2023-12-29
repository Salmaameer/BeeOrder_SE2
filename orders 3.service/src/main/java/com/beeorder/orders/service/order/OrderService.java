package com.beeorder.orders.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements OrderComponentService {
    OrderRepo orderRepo;
    public OrderService(OrderRepo o)
    {
        this.orderRepo = o;
    }    
    @Override
    public void addComponent(orderComponent o) {
        orderRepo.orderComponents.add(o);
    }
    @Override
    public String removeComponent(orderComponent o) {
    
        orderComponent pro = null ;
        for (orderComponent p : orderRepo.orderComponents) {
            if (p.getId() == o.getId()) {
                pro = p;
            }
        }
        if (pro != null) {
            orderRepo.orderComponents.remove(pro);
            return ("Product deleted");

        }else {
            return ("This product doesn't exist");
        }
    
    }

}
