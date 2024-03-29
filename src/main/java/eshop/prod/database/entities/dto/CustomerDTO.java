package eshop.prod.database.entities.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id_customer;
    private String name;
    private String email;
    private String address;
}
