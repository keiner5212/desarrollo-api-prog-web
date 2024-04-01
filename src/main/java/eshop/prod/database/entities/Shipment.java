package eshop.prod.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id_order")
    private Order order_id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String carrier;

    @Column(nullable = false)
    private String tracking_number;

    public void updateOnlyNecessary( Shipment updated) {
        if (updated.getOrder_id() != null && this.getOrder_id() != updated.getOrder_id()) {
            this.setOrder_id(updated.getOrder_id());
        }
        if (updated.getAddress() != null && this.getAddress() != updated.getAddress()) {
            this.setAddress(updated.getAddress());
        }
        if (updated.getCarrier() != null && this.getCarrier() != updated.getCarrier()) {
            this.setCarrier(updated.getCarrier());
        }
        if (updated.getTracking_number() != null && this.getTracking_number() != updated.getTracking_number()) {
            this.setTracking_number(updated.getTracking_number());
        }
    }
}
