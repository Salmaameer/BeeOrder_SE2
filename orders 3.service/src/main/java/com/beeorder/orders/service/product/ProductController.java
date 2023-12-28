package com.beeorder.orders.service.product;

import org.springframework.web.bind.annotation.*;
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
    public String addProduct(@RequestBody Product product) {
        // Logic to add product
        String returnStatus = prService.addProduct(product);

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
    public ResponseEntity<ProductRepo> getAllProducts(@PathVariable("id") int id) {
        // Logic to add product
        return ResponseEntity.ok( prService.getAllProducts());
    }
}
