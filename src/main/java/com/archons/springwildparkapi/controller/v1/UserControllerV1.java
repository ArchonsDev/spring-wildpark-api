package com.archons.springwildparkapi.controller.v1;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.model.User;
import com.archons.springwildparkapi.service.UserService;

@RestController
@RequestMapping("api/v1/users")
public class UserControllerV1 {
    private final UserService userService;

    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        Optional<User> userOptional = userService.getUserById(updatedUser.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setPassword(updatedUser.getPassword());
            user.setBirthdate(updatedUser.getBirthdate());
            user.setFirstname(updatedUser.getFirstname());
            user.setLastname(updatedUser.getLastname());
            user.setContactNumber(updatedUser.getContactNumber());
            user.setGender(updatedUser.getGender());
            user.setStreet(updatedUser.getStreet());
            user.setMunicipality(updatedUser.getMunicipality());
            user.setProvince(updatedUser.getProvince());
            user.setCountry(updatedUser.getCountry());
            user.setZipCode(updatedUser.getZipCode());

            userService.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
