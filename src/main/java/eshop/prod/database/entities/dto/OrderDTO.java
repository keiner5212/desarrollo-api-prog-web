package eshop.prod.database.entities.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class OrderDTO {
    private Long id_order;
    private Long customer_id;
    private Timestamp order_date;
    private String status;
}


/*
 json example
 {
    "customer_id": 1,
    "order_date": "2022-01-01 00:00:00",
    "status": "pending"
 }
 */
