package com.archons.springwildparkapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.OrganizationEntity;
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

    public boolean isOrganizationOwner(OrganizationEntity organization, AccountEntity account) {
        if (organization.getOwner().equals(account)) {
            return true;
        }

        return false;
    }

    public boolean isOrganizationAdmin(OrganizationEntity organization, AccountEntity account) {
        List<AccountEntity> organizationAdmins = organization.getAdmins();

        for (AccountEntity a : organizationAdmins) {
            if (a.equals(account)) {
                return true;
            }
        }

        return false;
    }

    public boolean isOrganizationMember(OrganizationEntity organization, AccountEntity account) {
        List<AccountEntity> organizationMembers = organization.getMembers();

        for (AccountEntity a : organizationMembers) {
            if (a.equals(account)) {
                return true;
            }
        }

        return false;
    }

    public LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(dateString, formatter);
    }
}
