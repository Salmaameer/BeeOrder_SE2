package com.beeorder.orders.service.account;

import org.springframework.web.bind.annotation.RestController;

import com.beeorder.orders.service.product.Product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/account")

public class AccountController
{
    public AccountService accountService;
    public AccountController(AccountService a)
    {
        this.accountService = a;
    }

    @GetMapping(value = "/get/{userName}")
    public Account getProduct(@PathVariable("userName") String userName) {
        // Logic to add product
        
        return accountService.accountExists(userName);
    }
}
