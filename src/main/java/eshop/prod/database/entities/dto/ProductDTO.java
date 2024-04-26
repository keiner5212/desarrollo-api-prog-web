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

/*
 json example
 {
    "name": "T-Shirt",
    "price": 10.99,
    "description": "A basic t-shirt",
    "brand": "T-Shirt Company",
    "image": "https://www.filmyvastra.com/wp-content/uploads/2019/06/Black-Wide-tshirt-without-hanger-Recovered-scaled.jpg",
    "stock": 100
 }
 */
