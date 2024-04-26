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

// CREATE TABLE customer (
//     id_customer SERIAL PRIMARY KEY,
//     name VARCHAR(255) NOT NULL,
//     email VARCHAR(255) NOT NULL,
//     address VARCHAR(255) NOT NULL,
    // password VARCHAR(255) NOT NULL,
    // role VARCHAR(255) NOT NULL
// );

@Entity
@Builder
@Data
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_customer;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    public void updateOnlyNecessary(Customer updated) {
        if (updated.getName() != null && this.getName() != updated.getName()) {
            this.setName(updated.getName());
        }
        if (updated.getEmail() != null && this.getEmail() != updated.getEmail()) {
            this.setEmail(updated.getEmail()); 
        }
        if (updated.getAddress() != null && this.getAddress() != updated.getAddress()) {
            this.setAddress(updated.getAddress()); 
        }
        if (updated.getPassword() != null && this.getPassword() != updated.getPassword()) {
            this.setPassword(updated.getPassword());
        }
        if (updated.getRole() != null && this.getRole() != updated.getRole()) {
            this.setRole(updated.getRole());
        }
    }
    
}
