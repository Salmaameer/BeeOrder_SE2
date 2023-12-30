package com.beeorder.orders.service.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
 public class PairDto {
    private String name;
    private int quantity;

}
@Setter @Getter
class RequestDTO {
    private List<PairDto> orderComponents;
    private List<String> userNames;


}
