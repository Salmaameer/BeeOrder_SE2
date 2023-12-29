package com.beeorder.orders.service.order;

import org.springframework.web.bind.annotation.RestController;

import com.beeorder.orders.service.product.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/order")


public class OrderController {
@Autowired
    OrderService orderService;
    public OrderController(OrderService o)
    {
        this.orderService = o;
    }
    // @PostMapping(value = "/addToOrder/{id}")
    // public String addProduct(@PathVariable("id") int id , @RequestBody Product product) {
    //     // Logic to add product

    //     String returnStatus = prService.addProduct(product);

    //     return returnStatus;
    // }
    @PostMapping(value =  "/createOrder")
    public void createOrder(@RequestBody List<orderComponent> orderComponents) {
        for(orderComponent o : orderComponents)
        {
            orderService.addComponent(o);
        }
    }
    
}
