package com.beeorder.orders.service.product;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RequestMapping("/api")
@RestController

public class ProductController {
    @Autowired
    private ProductService prService;

    public ProductController(ProductService s) {
        this.prService = s;
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
