package com.archons.springwildparkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.Role;

public class BaseService {
    private final PasswordEncoder passwordEncoder;

    public BaseService() {
        this.passwordEncoder = null;
    }

    @Autowired
    public BaseService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isAccountAdmin(AccountEntity account) {
        return account.getRole() == Role.ADMIN;
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
