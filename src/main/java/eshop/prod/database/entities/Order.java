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
import java.sql.Timestamp;

// CREATE TABLE orders (
//     id_order SERIAL PRIMARY KEY,
//     customer_id INTEGER NOT NULL REFERENCES customer(id_customer),
//     order_date TIMESTAMP NOT NULL CHECK (order_date > now()),
//     status VARCHAR(255) NOT NULL CHECK (status IN ('PENDIENTE', 'ENVIADO', 'ENTREGADO'))
// );

@Entity
@Builder
@Data
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_order;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id_customer")
    private Customer customer_id;

    @Column(nullable = false)
    private Timestamp order_date;

    @Column(nullable = false)
    private String status;

    public void updateOnlyNecessary(Order updated) {
        if (updated.getCustomer_id() != null && this.getCustomer_id() != updated.getCustomer_id()) {
            this.setCustomer_id(updated.getCustomer_id());
        }
        if (updated.getOrder_date() != null && this.getOrder_date() != updated.getOrder_date()) {
            this.setOrder_date(updated.getOrder_date());
        }
        if (updated.getStatus() != null && this.getStatus() != updated.getStatus()) {
            this.setStatus(updated.getStatus());
        }
    }
}
