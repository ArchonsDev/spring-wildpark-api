package com.archons.springwildparkapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.AccountEntity;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {

}
