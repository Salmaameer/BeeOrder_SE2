
package com.beeorder.orders.service.product;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Product {
    private String name;
    private int serialNumber;
    private double price;
    private int quantity;
    private String category;
    private String vendor;
}
