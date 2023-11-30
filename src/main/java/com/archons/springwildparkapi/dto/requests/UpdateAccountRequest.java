package com.archons.springwildparkapi.dto.requests;

import com.archons.springwildparkapi.model.AccountEntity;

public class UpdateAccountRequest {
    private AccountEntity updatedAccount;

    public UpdateAccountRequest() {
    }

    public UpdateAccountRequest(AccountEntity updatedAccount) {
        this.updatedAccount = updatedAccount;
    }

    public AccountEntity getUpdatedAccount() {
        return updatedAccount;
    }

    public void setUpdatedAccount(AccountEntity updatedAccount) {
        this.updatedAccount = updatedAccount;
    }
}
