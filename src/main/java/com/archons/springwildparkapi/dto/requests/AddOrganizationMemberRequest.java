package com.archons.springwildparkapi.dto.requests;

public class AddOrganizationMemberRequest {
    private int accountId;

    public AddOrganizationMemberRequest() {
    }

    public AddOrganizationMemberRequest(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
