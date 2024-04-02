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

import java.sql.Timestamp;

// CREATE TABLE payment (
//     id_payment SERIAL PRIMARY KEY,
//     order_id INTEGER NOT NULL REFERENCES orders(id_order),
//     amount NUMERIC(10, 2) NOT NULL,
//     payment_date TIMESTAMP NOT NULL CHECK (payment_date > now()),
//     payment_method VARCHAR(255) NOT NULL CHECK (payment_method IN ('EFECTIVO', 'TARJETA_CREDITO', 'PAYPAL', 'NEQUI', 'DAVIPLATA', 'PSE'))
// );

@Entity
@Builder
@Data
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_payment;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id_order")
    private Order order_id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Timestamp payment_date;

    @Column(nullable = false)
    private String payment_method;

    public void updateOnlyNecessary(Payment updated) {
        if (updated.getOrder_id() != null && this.getOrder_id() != updated.getOrder_id()) {
            this.setOrder_id(updated.getOrder_id());
        }
        if (updated.getAmount() != null && this.getAmount() != updated.getAmount()) {
            this.setAmount(updated.getAmount());
        }
        if (updated.getPayment_date() != null && this.getPayment_date() != updated.getPayment_date()) {
            this.setPayment_date(updated.getPayment_date());
        }
        if (updated.getPayment_method() != null && this.getPayment_method() != updated.getPayment_method()) {
            this.setPayment_method(updated.getPayment_method());
        }
    }
}
