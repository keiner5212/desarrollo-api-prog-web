package eshop.prod.database.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Order;
import eshop.prod.database.entities.OrderItem;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {
    /* search for orders between two dates */
    @Query("SELECT f FROM Order f WHERE f.order_date >= ?1 AND f.order_date <= ?2")
    Optional<List<Order>> findByDateBetween(Timestamp date1, Timestamp date2);

    /* Search orders by customer and a status */
    @Query("SELECT o FROM Order o JOIN FETCH o.customer_id c WHERE c.id_customer = ?1 AND lower(o.status) = lower(?2)")
    Optional<List<Order>> findByCustomerAndStatus(long customer, String status);

    /* retrieve orders with their items using JOIN fetch */
    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.order_id o WHERE o.id_order = ?1")
    Optional<List<OrderItem>> findByIdWithOrderItems(long id);

    /* Order by id customer */
    @Query("SELECT o FROM Order o JOIN FETCH o.customer_id c WHERE c.id_customer = ?1")
    Optional<List<Order>> findAllOrderByCustomerId(long id);
}
