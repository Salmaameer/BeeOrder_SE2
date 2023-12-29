package com.beeorder.orders.service.product;


import java.util.List;
import java.util.Map;

import com.beeorder.orders.service.order.orderComponent;
import com.beeorder.orders.service.product.ProductRepo;
import org.springframework.stereotype.Service;

import com.beeorder.orders.service.order.OrderComponentService;


@Service
public class ProductService implements OrderComponentService {
    private ProductRepo repos;
    
    public ProductService(ProductRepo repos) {

        this.repos = repos;
    }
    public String addProduct(List<Product> productsToAdd)
    {
        for(Product p : productsToAdd){
            repos.products.add(p);
        }
        
        return ("Products added");
    }

    public String addProduct(Product productToAdd)
    {
        repos.products.add(productToAdd);
        return ("Product added");
    }
    public String deleteProduct(int id)
    {
        Product pro = null ;
        for (Product p : repos.products) {
            if (p.getId() == id) {
                pro = p;
            }
        }
        if (pro != null) {
            repos.products.remove(pro);
            return ("Product deleted");

        }else {
            return ("This product doesn't exist");
        }
        
    }
    public Product getProduct( int id)
    {
        Product pro = null ;
        for (Product p : repos.products) {
            if (p.getId() == id) {
                pro = p;
            }
        }
    
            return pro;
        
    }

    public ProductRepo getAllProducts()
    {
        return repos;
    }

    @Override
    public void addComponent(int id,orderComponent o) {

        throw new UnsupportedOperationException("Unimplemented method 'addComponent'");
    }
    @Override
    public String removeComponent(int id,orderComponent o) {

        throw new UnsupportedOperationException("Unimplemented method 'removeComponent'");
    }
    
}
