package eshop.prod.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.OrderItem;

@RepositoryRestResource
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    /** find by order id */
    @Query("SELECT o FROM OrderItem o WHERE o.order_id.id_order = ?1")
    Optional<List<OrderItem>> findByOrderId(long orderId);

    /** find by product id */
    @Query("SELECT o FROM OrderItem o WHERE o.product_id.id_product = ?1")
    Optional<List<OrderItem>> findByProductId(long productId);

    /** fund sum selled by product id */
    @Query("SELECT SUM(o.quantity) FROM OrderItem o WHERE o.product_id.id_product = ?1")
    Optional<Integer> findTotalSellByProductId(long productId);
}
