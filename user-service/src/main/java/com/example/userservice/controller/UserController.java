package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.service.UserEventProducer;
import com.example.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;


    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping("/getUserInfo")
    public Optional<User> getUserInfo(@RequestParam Long userId) {        
        return service.findUser(userId);
    }

    @GetMapping("/getAllUserInfo")
    public List<User> getAllUserInfo() {
        return service.findAllUser();

    }


}
