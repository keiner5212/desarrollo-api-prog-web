package eshop.prod.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// CREATE TABLE product (
//     id_product SERIAL PRIMARY KEY,
//     name VARCHAR(255) NOT NULL,
//     price NUMERIC(10, 2) NOT NULL,
//     description VARCHAR(255) NOT NULL,
//     brand VARCHAR(255) NOT NULL,
//     image TEXT NOT NULL,
//     stock INTEGER NOT NULL
// );

@Entity
@Builder
@Data
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_product;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Integer stock;

    public void updateOnlyNecessary(Product updated) {
        if (updated.getName() != null && this.getName() != updated.getName()) {
            this.setName(updated.getName());
        }
        if (updated.getPrice() != null && this.getPrice() != updated.getPrice()) {
            this.setPrice(updated.getPrice());
        }
        if (updated.getDescription() != null && this.getDescription() != updated.getDescription()) {
            this.setDescription(updated.getDescription());
        }
        if (updated.getBrand() != null && this.getBrand() != updated.getBrand()) {
            this.setBrand(updated.getBrand());
        }
        if (updated.getImage() != null && this.getImage() != updated.getImage()) {
            this.setImage(updated.getImage());
        }
        if (updated.getStock() != null && this.getStock() != updated.getStock()) {
            this.setStock(updated.getStock());
        }
    }
}
