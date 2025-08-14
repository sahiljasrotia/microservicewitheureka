package com.example.userservice.service;

import com.example.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User createUser(User user);
    public Optional<User> findUser(Long userId);
    public List<User> findAllUser();
    public void updateUserBalance(Long userId, Double amount);

}
