package com.beeorder.orders.service.order;

import ch.qos.logback.core.joran.sanity.Pair;
import org.springframework.web.bind.annotation.RestController;

import com.beeorder.orders.service.product.Product;
import com.beeorder.orders.service.product.ProductRepo;
import com.beeorder.orders.service.product.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private OrderService orderService;
    private ProductService prService;

    public OrderController(OrderService o , ProductService s)
    {
        this.orderService = o;
        this.prService = s;
    }

    @PostMapping(value =  "/createorder")
    public Order createOrder(@RequestBody List<PairDto> orderComponents) {
        return  orderService.makeOrder(prService,orderComponents);
    }

    

    @PostMapping(value = "/addProduct")
    public String addProduct(@RequestBody List<Product> products) {
        // Logic to add product
        String returnStatus = prService.addProduct(products);
        return returnStatus;

    }

    
    @DeleteMapping("/delete/{id}")
    public  String deletePerson(@PathVariable("id") int id) {
        System.out.println("in delete with id:"+id);
        String returnStatus = prService.deleteProduct(id);
        
        return returnStatus;

    }

    @GetMapping(value = "/get/{id}")
    public Product getProduct(@PathVariable("id") int id) {
        // Logic to add product
        
        return prService.getProduct(id);
    }

    @GetMapping(value = "/getAll")
    public ProductRepo getAllProducts() {
        // Logic to add product
        return prService.getAllProducts();
    }
    
}
