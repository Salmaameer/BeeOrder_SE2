package com.beeorder.orders.service.signin;

import com.beeorder.orders.service.account.Account;
import com.beeorder.orders.service.account.AccountRepo;
import org.springframework.stereotype.Service;

@Service
public class SignInService implements ISignin{

    private AccountRepo repo;

    public SignInService(AccountRepo repo){
        this.repo = repo;
    }

    @Override
    public Account signIn(String uName , String pass ) {
        for (Account acc : repo.accounts) {
            if (acc.getUserName().equals(uName) && (acc.getPassword().equals(pass))) {
                System.out.printf("authorized");
                return acc;
            }

        }

        System.out.println("not found");
        return null;
    }


}
