package com.archons.springwildparkapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Iterable<AccountEntity> getAllUsers() {
        return accountRepository.findAll();
    }

    public Optional<AccountEntity> getUserById(int id) {
        return accountRepository.findById(id);
    }

    public boolean addUser(AccountEntity user) {
        accountRepository.save(user);
        return true;
    }

}
