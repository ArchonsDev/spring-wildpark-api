package com.archons.springwildparkapi.dto.requests;

public class AccountRequest {
    private int accountId;

    public AccountRequest(int accountId) {
        this.accountId = accountId;
    }

    public AccountRequest() {
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
