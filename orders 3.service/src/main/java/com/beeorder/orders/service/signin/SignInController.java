package com.beeorder.orders.service.signin;


import com.beeorder.orders.service.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signin")
public class SignInController {
    @Autowired
    private ISignin signInService;

    public SignInController(ISignin signInSrve){
        this.signInService = signInSrve;

    }

    //Http?name
    @GetMapping("/authorizing")
    public Account signIn(@RequestParam ( "userName") String usrName ,@RequestParam  ("password") String usrPass){

        return signInService.signIn(usrName,usrPass);
    }


}
