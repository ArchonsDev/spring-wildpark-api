package com.archons.springwildparkapi.dto.reesponses;

import java.util.List;

import com.archons.springwildparkapi.model.AccountEntity;

public class OrganizationMemberResponse {
    private AccountEntity owner;
    private List<AccountEntity> admins;
    private List<AccountEntity> members;

    public OrganizationMemberResponse() {
    }

    public OrganizationMemberResponse(AccountEntity owner, List<AccountEntity> admins, List<AccountEntity> members) {
        this.owner = owner;
        this.admins = admins;
        this.members = members;
    }

    public AccountEntity getOwner() {
        return owner;
    }

    public void setOwner(AccountEntity owner) {
        this.owner = owner;
    }

    public List<AccountEntity> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AccountEntity> admins) {
        this.admins = admins;
    }

    public List<AccountEntity> getMembers() {
        return members;
    }

    public void setMembers(List<AccountEntity> members) {
        this.members = members;
    }
}
