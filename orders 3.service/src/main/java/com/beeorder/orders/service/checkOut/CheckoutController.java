package com.beeorder.orders.service.checkOut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beeorder.orders.service.order.OrderController;
import com.beeorder.orders.service.order.OrdersInventory;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/{id}")
    public String checkout(@PathVariable("id") int id){

        return checkoutService.checkOutProcess(id);
    }

}
