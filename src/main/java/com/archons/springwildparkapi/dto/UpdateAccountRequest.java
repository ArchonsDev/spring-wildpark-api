package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.AccountEntity;

public class UpdateAccountRequest {
    private int requesterId;
    private AccountEntity updatedAccount;

    public UpdateAccountRequest() {
    }

    public UpdateAccountRequest(int requesterId, AccountEntity updatedAccount) {
        this.requesterId = requesterId;
        this.updatedAccount = updatedAccount;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public AccountEntity getUpdatedAccount() {
        return updatedAccount;
    }

    public void setUpdatedAccount(AccountEntity updatedAccount) {
        this.updatedAccount = updatedAccount;
    }
}
