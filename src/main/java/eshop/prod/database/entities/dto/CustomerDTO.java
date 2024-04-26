package eshop.prod.database.entities.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id_customer;
    private String name;
    private String email;
    private String address;
    private String password;
    private String role;
}

/*
 json example
 {
    "name": "John Doe",
    "email": "HbqFP@example.com",
    "address": "123 Main St, Anytown USA",
    "password": "password"
 }
 */
