package com.example.userservice.controller;

import com.example.userservice.service.UserEventProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserEventProducer producer;

    public UserController(UserEventProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/info")
    public String userInfo() {
        return "User info from UserService";
    }


    @PostMapping("/create")
    public String createUser(@RequestParam("userId") String userId) {
        producer.sendUserCreatedEvent(userId);
        return "User created and event sent";
    }
}
