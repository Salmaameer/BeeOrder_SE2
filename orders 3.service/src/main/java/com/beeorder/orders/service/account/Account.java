package com.beeorder.orders.service.account;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Random;


@Getter @Setter

public class Account {

    private Long id;
    private  String userName;
    private String email;
    private String phone;
    private double balance;
    private String password;


    public Account(){
        Random rand = new Random();
        long randomInt = rand.nextInt(10000);
        this.id = randomInt;
    }


}
