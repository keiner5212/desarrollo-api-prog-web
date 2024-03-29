package eshop.prod.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// CREATE TABLE shipment (
//     id_shipment SERIAL PRIMARY KEY,
//     order_id INTEGER NOT NULL REFERENCES orders(id_order),
//     address VARCHAR(255) NOT NULL,
//     carrier VARCHAR(255) NOT NULL,
//     tracking_number VARCHAR(255) NOT NULL
// );

@Entity
@Builder
@Data
@Table(name = "shipment")
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_shipment;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id_order")
    private Order order_id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String carrier;

    @Column(nullable = false)
    private String tracking_number;
}
