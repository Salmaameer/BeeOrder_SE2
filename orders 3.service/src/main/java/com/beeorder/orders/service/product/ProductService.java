package com.beeorder.orders.service.product;


import java.util.Map;
import com.beeorder.orders.service.product.ProductRepo;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    private ProductRepo repos;
    
    public ProductService(ProductRepo repos) {
        this.repos = repos;
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
            if (p.getSerialNumber() == id) {
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
            if (p.getSerialNumber() == id) {
                pro = p;
            }
        }
    
            return pro;
        
    }

    public ProductRepo getAllProducts()
    {
        return repos;
    }
    
}
