package eshop.prod.database.entities.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id_order_item;
    private Long order_id;
    private Long product_id;
    private Integer quantity;
    private Double unit_price;
}