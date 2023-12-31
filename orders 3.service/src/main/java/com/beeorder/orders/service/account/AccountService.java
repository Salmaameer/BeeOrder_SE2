package com.beeorder.orders.service.account;

import org.springframework.stereotype.Service;

@Service
public class AccountService
{
    public  AccountRepo accountRepo;
    public AccountService(AccountRepo o)
    {
        this.accountRepo=o;
    }
    //checks if account exists,used for authorization later
    public Account accountExists(String userName)
    {
        for(Account a : accountRepo.accounts)
        {
            if(a.getUserName().equals(userName))return a;
        }
        return null;
    }
}
