package com.beeorder.orders.service.order;


import com.beeorder.orders.service.account.Account;
import com.beeorder.orders.service.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SimpleOrder  extends orderComponent{
    private int id;
    private double totalCost;
    public  List<Product> orderProduct = new ArrayList<>();
    public Account orderAccount;


    public orderComponent  viewDetails() {
       for (Product o : orderProduct ){
          return  o.viewDetails();
       }
        return null;
    }
}
