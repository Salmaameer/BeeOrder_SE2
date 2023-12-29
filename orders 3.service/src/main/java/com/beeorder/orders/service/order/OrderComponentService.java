package com.beeorder.orders.service.order;

import org.springframework.stereotype.Service;

@Service
public interface OrderComponentService 
{
   // public void viewDetails(int id);
    public void addComponent(int id,orderComponent o);
    public String removeComponent(int id,orderComponent o);
} 