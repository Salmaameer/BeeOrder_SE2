package com.beeorder.orders.service.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
// helper class for creating orders
// where user provides array of names of products and quantity of these products
@Getter@Setter
public class PairDto {
    private String name;
    private int quantity;

}
// this array of usernames carries the usernames that are creating the order
// whether a simple order where it contains only one name
// or more than one name which means it is a compound order
@Setter @Getter
class RequestDTO {
    private List<PairDto> orderComponents;
    private List<String> userNames;


}
