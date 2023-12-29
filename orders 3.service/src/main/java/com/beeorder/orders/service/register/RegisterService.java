package com.beeorder.orders.service.register;

import org.springframework.stereotype.Service;

import com.beeorder.orders.service.account.*;



@Service
public class RegisterService implements IRegister {

    private AccountRepo repo;


    public RegisterService(AccountRepo repo) {
        this.repo = repo;
    }


    @Override
    public String register(Account regAccount){


    for (Account acc : repo.accounts) {
        if (acc.getUserName().equals(regAccount.getUserName())) {
            return ("Account already exists");
        }
        
    }
    repo.accounts.add(regAccount);
        for (Account acc : repo.accounts) {
            System.out.println("name " + acc.getUserName());
            System.out.println("bal " + acc.getBalance());
        }

    return ("Account registerd");
    }


}
