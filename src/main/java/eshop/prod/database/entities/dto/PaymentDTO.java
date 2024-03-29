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