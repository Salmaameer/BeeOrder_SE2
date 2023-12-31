package com.beeorder.orders.service.checkOut;

import com.beeorder.orders.service.account.Account;
import com.beeorder.orders.service.notification.NotificationQueue;
import com.beeorder.orders.service.notification.ShipmentNotification;
import com.beeorder.orders.service.order.OrdersInventory;
import com.beeorder.orders.service.order.orderComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService 
{
    @Autowired
    private OrdersInventory ordersInventory;
    
    public orderComponent getOrder(int id)
    {
        for(orderComponent order: ordersInventory.getOrders())
        {
            if(order.getId() == id)
            {
                return order;
            }
        }
        return null;
    }

    public String checkOutProcess(int id, NotificationQueue notifyQueue )
    {
       
        orderComponent myOrder = getOrder(id);
        if(myOrder==null)
        {
            return "Order with id = "+id + " doesn't exist"; 
        }
        else
        {
            myOrder.deductFromBalance(notifyQueue);
            
        }

        return "order has been successfully placed and ready to be shipped:)";
    }
}
