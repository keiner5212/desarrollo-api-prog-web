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
