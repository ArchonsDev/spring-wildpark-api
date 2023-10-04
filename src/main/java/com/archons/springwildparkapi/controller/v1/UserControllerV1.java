package com.archons.springwildparkapi.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archons.springwildparkapi.model.User;
import com.archons.springwildparkapi.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserControllerV1 {
    private final UserService userService;

    @Autowired
    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        return userService.addUser(user) ? "saved" : "failed";
    }
}
