package eshop.prod.database.entities.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id_product;
    private String name;
    private Double price;
    private String description;
    private String brand;
    private String image;
    private Integer stock;
}