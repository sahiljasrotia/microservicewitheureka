package com.example.userservice.service.impl;

import com.example.userservice.model.User;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.UserEventProducer;
import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserEventProducer producer;
    private final UserRepo userRepo;

    public UserServiceImpl(UserEventProducer producer, UserRepo userRepo) {
        this.producer = producer;
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user) {
        log.info("Creating new user: {}", user.getName());

        // Persist the user first
        User savedUser = userRepo.save(user);

        // Emit event after successful save
        try {
            producer.sendUserCreatedEvent(savedUser);
            log.info("UserCreatedEvent sent for user ID: {}", savedUser.getId());
        } catch (Exception e) {
            log.error("Failed to send UserCreatedEvent for user ID: {}", savedUser.getId(), e);
            // Optionally: trigger compensation or retry logic
        }

        return savedUser;
    }

    public Optional<User> findUser(Long userId) {
        return userRepo.findById(userId);
    }

    @Override
    public List<User> findAllUser() {
        return userRepo.findAll();
    }

    @Override
    public void updateUserBalance(Long userId, Double amount) {
        userRepo.findById(userId)
                .map(user -> {
                    user.setAmount(user.getAmount() - amount);
                    return userRepo.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));


    }
}
