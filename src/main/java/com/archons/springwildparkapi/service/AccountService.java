package com.archons.springwildparkapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archons.springwildparkapi.dto.AccountUpdateRequest;
import com.archons.springwildparkapi.exceptions.InsufficientPrivillegesException;
import com.archons.springwildparkapi.model.AccountEntity;
import com.archons.springwildparkapi.model.Role;
import com.archons.springwildparkapi.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountEntity> getAllAccounts(AccountEntity requester) throws InsufficientPrivillegesException {
        // Checks if the requester is an admin
        if (requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        Iterable<AccountEntity> iterable = accountRepository.findAll();
        List<AccountEntity> userList = new ArrayList<>();
        iterable.forEach(userList::add);
        return userList;
    }

    public Optional<AccountEntity> getAccountById(int id) {
        return accountRepository.findById(id);
    }

    public Optional<AccountEntity> updateAccount(AccountUpdateRequest accountUpdateRequest, int accountId)
            throws InsufficientPrivillegesException {
        AccountEntity requester = accountUpdateRequest.getRequester();
        AccountEntity updatedAccount = accountUpdateRequest.getUpdatedAccount();

        if (requester.getId() != updatedAccount.getId() && requester.getRole() != Role.ADMIN) {
            throw new InsufficientPrivillegesException();
        }

        Optional<AccountEntity> existingAccount = accountRepository.findById(accountId);

        if (!existingAccount.isPresent() || requester.getRole() != Role.ADMIN) {
            return Optional.ofNullable(null);
        }

        return Optional.of(accountRepository.save(updatedAccount));
    }

    public boolean deleteAccount(int accountId) {
        Optional<AccountEntity> existingAccount = accountRepository.findById(accountId);

        if (existingAccount.isPresent()) {
            accountRepository.delete(existingAccount.get());
            return true;
        }

        return false;
    }
}
