package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@SpringBootApplication
@RestController
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

//    @PostMapping("/login")
//    public String login(@RequestBody AuthRequest authRequest) {
//        System.out.println(authRequest.getUsername());
//        if ("admin".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())) {
//            return JwtUtil.generateToken(authRequest.getUsername());
//        } else {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }
//
//    @GetMapping("/gateway/user")
//    public ResponseEntity<String> getUserInfo(@RequestHeader("Authorization") String token) {
//        JwtUtil.validateToken(token);
//        return ResponseEntity.ok("UserService response from Gateway");
//    }
////
//    @GetMapping("/gateway/order")
//    public ResponseEntity<String> getOrderInfo(@RequestHeader("Authorization") String token) {
//        JwtUtil.validateToken(token);
//        return ResponseEntity.ok("OrderService response from Gateway");
//    }

@GetMapping("/gateway/user")
public ResponseEntity<String> getUserInfo() {
    return ResponseEntity.ok("UserService response from Gateway");
}
}
