package com.archons.springwildparkapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.archons.springwildparkapi.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
