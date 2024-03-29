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

// CREATE TABLE order_item (
//     id_order_item SERIAL PRIMARY KEY,
//     order_id INTEGER NOT NULL REFERENCES orders(id_order),
//     product_id INTEGER NOT NULL REFERENCES product(id_product),
//     quantity INTEGER NOT NULL,
//     unit_price NUMERIC(10, 2) NOT NULL
// );

@Entity
@Builder
@Data
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_order_item;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id_order")
    private Order order_id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id_product")
    private Product product_id;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private Double unit_price;
}
