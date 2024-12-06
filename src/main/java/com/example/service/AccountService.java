package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateException;
import com.example.exception.NullException;
import com.example.exception.UnAuthException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account registerAccount(Account account) {

        if (account.getUsername().isBlank() || account.getUsername().equals(null)
                || account.getPassword().length() < 5) {
            throw new NullException("Error");
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            String name = accountRepository.findByUsername(account.getUsername()).get().getUsername();
            if (account.getUsername().equals(name)) {
                throw new DuplicateException("Duplicate");
            }
        } else {
            return accountRepository.save(account);
        }
        return account;

    }

    public Account loginAccount(Account account) {
        Optional<Account> temp = accountRepository.findByUsername(account.getUsername());
        if(temp.isPresent()){
            if(temp.get().getUsername().equals(account.getUsername()) && temp.get().getPassword().equals(account.getPassword())){
                return temp.get();
            }
           
        }
        throw new UnAuthException("No Account");
    }
}
