package eshop.prod.database.entities.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class PaymentDTO {
    private Long id_payment;
    private Long order_id;
    private Double amount;
    private Timestamp payment_date;
    private String payment_method;
}

/*
 json example
 {
    "order_id": 1,
    "amount": 9.99,
    "payment_date": "2022-01-01 00:00:00",
    "payment_method": "TARJETA_CREDITO"
 }
 */
