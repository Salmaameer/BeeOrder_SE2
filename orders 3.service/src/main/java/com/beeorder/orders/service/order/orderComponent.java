package com.beeorder.orders.service.order;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public abstract class orderComponent
{
    int id;
    public abstract orderComponent viewDetails();

}
