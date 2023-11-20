package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.AccountEntity;

public class AuthenticationResponse {
    public String token;
    public AccountEntity account;

    public AuthenticationResponse(String token, AccountEntity account) {
        this.token = token;
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
