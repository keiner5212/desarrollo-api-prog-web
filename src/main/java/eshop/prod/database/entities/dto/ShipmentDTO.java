package eshop.prod.database.entities.dto;

import lombok.Data;

@Data
public class ShipmentDTO {
    private Long id_shipment;
    private Long order_id;
    private String address;
    private String carrier;
    private String tracking_number;
}

/*
 json example
 {
    "order_id": 1,
    "address": "123 Main St, Anytown USA",
    "carrier": "Fedex",
    "tracking_number": "123456789"
 }
 */
