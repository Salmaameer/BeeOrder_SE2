package com.beeorder.orders.service.order;

import ch.qos.logback.core.joran.sanity.Pair;
import com.beeorder.orders.service.account.AccountService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/order")


public class OrderController {

    @Autowired
    public ProductService prService;
@Autowired
    private OrderService orderService;
@Autowired
    private AccountService AccService;



    public OrderController(OrderService o , ProductService s, AccountService accSer)
    {
        this.orderService = o;
        this.prService = s;
        this.AccService = accSer;
    }

    @PostMapping(value =  "/createorder")
    public String createOrder(@RequestBody RequestDTO request) {

        String feedBack = orderService.authorizeUsers(AccService , request.getUserNames());
        System.out.println(feedBack);
        if (!feedBack.equals("All users Found")) return null;
        else {
            // this is single order
            if(request.getUserNames().size() == 1 ){

                return orderService.makeSimple(prService,request.getOrderComponents());
            }
            return null;
            // return orderService.makeCompoundOrder(prService,request.getOrderComponents());
        }
    }

    @DeleteMapping(value = "/cancelOrder/{id}")
    public String cancelOrder(@PathVariable("id") int id){
        return orderService.cancelOrder(id ,prService);
    }

    

//    @PostMapping(value = "/addProduct")
//    public String addProduct(@RequestBody List<Product> products) {
//        // Logic to add product
//        String returnStatus = prService.addProduct(products);
//        return returnStatus;
//
//    }

    
//    @DeleteMapping("/delete/{id}")
//    public  String deleteProduct(@PathVariable("id") int id) {
//        System.out.println("in delete with id:"+id);
//        String returnStatus = prService.deleteProduct(id);
//
//        return returnStatus;
//    }

//    @GetMapping(value = "/get/{id}")
//    public Product getProduct(@PathVariable("id") int id) {
//        // Logic to add product
//
//        return prService.getProduct(id);
//    }

//    @GetMapping(value = "/getAll")
//    public ProductRepo getAllProducts() {
//        // Logic to add product
//        return prService.getAllProducts();
//    }
    
}
