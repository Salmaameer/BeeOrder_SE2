package com.beeorder.orders.service.order;

import java.util.List;

import com.beeorder.orders.service.account.Account;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Order extends orderComponent  {

    public List<orderComponent> orderComponents;
    public Account orderAccount;

    @Override
    public orderComponent viewDetails() {
        for(orderComponent o : orderComponents)
        {
            return o.viewDetails();
        }
        return null;
    }
}
