package eshop.prod.database.repository;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Customer;
import eshop.prod.database.entities.Order;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {
    /*search for orders between two dates*/
    @Query("SELECT f FROM Order f WHERE f.date >= ?1 AND f.date <= ?2")
    List<Order> findByDateBetween(Timestamp date1, Timestamp date2);
    /*Search orders by customer and a status */
    @Query("SELECT o FROM Order o WHERE o.customer_id = ?1 AND o.status = ?2")
    List<Order> findByCustomerAndStatus(Customer customer, String status);
    /*retrieve orders with their items using JOIN fetch */
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi WHERE o.id_order = ?1")
    Order findByIdWithOrderItems(Long id);
}
