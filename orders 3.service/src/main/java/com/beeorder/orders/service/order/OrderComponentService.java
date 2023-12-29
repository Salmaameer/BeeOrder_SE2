package com.beeorder.orders.service.order;

import org.springframework.stereotype.Service;

@Service
public interface OrderComponentService 
{
   // public void viewDetails(int id);
    public void addComponent(orderComponent o);
    public String removeComponent(orderComponent o);
} 