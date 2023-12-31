package com.beeorder.orders.service.order;

import java.util.ArrayList;
import java.util.List;

import com.beeorder.orders.service.account.Account;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Order  extends  orderComponent{

    private int id;
    public List<SimpleOrder> orderComponents = new ArrayList<>();



    public orderComponent viewDetails() {
        for(SimpleOrder o : orderComponents)
        {
            return o.viewDetails();
        }
        return null;
    }

    public void addComponent(SimpleOrder orderComp){
        orderComponents.add(orderComp);
    }
}
