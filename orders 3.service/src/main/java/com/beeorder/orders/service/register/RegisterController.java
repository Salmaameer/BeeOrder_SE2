package com.beeorder.orders.service.register;


import com.beeorder.orders.service.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")

public class RegisterController {
    @Autowired
    private RegisterService regService;

    public RegisterController(RegisterService ser){
        this.regService = ser;
    }

    @PostMapping(value = "/add")
    public String registerNewAccount(@RequestBody Account account){
        return regService.register(account);
    }



    
}
