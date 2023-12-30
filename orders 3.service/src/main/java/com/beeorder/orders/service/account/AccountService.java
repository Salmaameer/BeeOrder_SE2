package com.beeorder.orders.service.account;

import org.springframework.stereotype.Service;

@Service
public class AccountService
{
    private AccountRepo accountRepo;
    public AccountService(AccountRepo o)
    {
        this.accountRepo=o;
    }
    public Account accountExists(String userName)
    {
        for(Account a : accountRepo.accounts)
        {
            if(a.getUserName().equals(userName))return a;
        }
        return null;
    }
}
