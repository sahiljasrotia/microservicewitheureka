package com.example.orderservice.dto;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ReqOrderDto {
    private Long userId;
    private String productName;
    private int quantity;
    private double price;
}
