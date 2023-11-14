package com.archons.springwildparkapi.dto;

import com.archons.springwildparkapi.model.AccountEntity;

public class AccountUpdateRequest {
    private AccountEntity requester;
    private AccountEntity updatedAccount;

    public AccountUpdateRequest() {
    }

    public AccountUpdateRequest(AccountEntity requester, AccountEntity updatedAccount) {
        this.requester = requester;
        this.updatedAccount = updatedAccount;
    }

    public AccountEntity getRequester() {
        return requester;
    }

    public void setRequester(AccountEntity requester) {
        this.requester = requester;
    }

    public AccountEntity getUpdatedAccount() {
        return updatedAccount;
    }

    public void setUpdatedAccount(AccountEntity updatedAccount) {
        this.updatedAccount = updatedAccount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((requester == null) ? 0 : requester.hashCode());
        result = prime * result + ((updatedAccount == null) ? 0 : updatedAccount.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountUpdateRequest other = (AccountUpdateRequest) obj;
        if (requester == null) {
            if (other.requester != null)
                return false;
        } else if (!requester.equals(other.requester))
            return false;
        if (updatedAccount == null) {
            if (other.updatedAccount != null)
                return false;
        } else if (!updatedAccount.equals(other.updatedAccount))
            return false;
        return true;
    }
}
