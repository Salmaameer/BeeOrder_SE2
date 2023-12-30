package com.beeorder.orders.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.beeorder.orders.service.product.*;
import ch.qos.logback.core.joran.sanity.Pair;
import com.beeorder.orders.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements OrderComponentService {
    OrdersInventory ordersInventory;

    public OrderService(OrdersInventory o ) {
        this.ordersInventory = o;
    }

    @Override
    public void addComponent(int id, orderComponent comp) {

        for (Order o : ordersInventory.orders) {
            if (o.getId() == id) {
                o.orderComponents.add(comp);
            }

        }
    }

    @Override
    public String removeComponent(int id, orderComponent comp) {

        for (Order o : ordersInventory.orders) {
            if (o.getId() == id) {
                o.orderComponents.remove(comp);
                return ("Product deleted");
            }
        }

        return ("This product doesn't exist");

    }

    // public Order makeOrder(List<orderComponent> orderComp){
    // //initialize new id
    // Order newOrder = new Order();
    // Random rand = new Random();
    // int randomInt = rand.nextInt(10000);
    // newOrder.id = randomInt;
    // //newOrder.creationDate()
    //
    // for (orderComponent comp : orderComp){
    // newOrder.orderComponents.add(comp);
    // }
    //
    // return newOrder;
    // }
    //
    // }
    public boolean  isExist(int id)
    {
        return true;
    }
    public void authorizeUser(ProductService productService, List<PairDto> orderComp,String username)
    {

    }

    public Order makeOrder(ProductService productService, List<PairDto> orderComp) {
        ProductRepo productRepo = productService.repos;

        // initialize new id
        Order newOrder = new Order();
        Random rand = new Random();
        int randomInt = rand.nextInt(10000);
        newOrder.id = randomInt;
        // newOrder.creationDate()
        newOrder.orderComponents = new ArrayList<>();

        for (PairDto pair : orderComp) {
            for (Product prod : productRepo.products) {
                if (pair.getName().equals(prod.getName())) {
                    if (prod.getQuantity() >= pair.getQuantity()) {
                        newOrder.orderComponents.add(prod);
                        prod.setQuantity(prod.getQuantity() - pair.getQuantity()); // update the quantity
                    }

                } else {
                    System.out.println("Can't add this item to the order");
                }
            }
        }

        return newOrder;
    }

}
