package eshop.prod.database.repository;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Payment;

@RepositoryRestResource
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    /*Retrieve payments within a date range */
    @Query("SELECT p FROM Payment p WHERE p.date >= ?1 AND p.date <= ?2")
    List<Payment> findByDateBetween(Timestamp date1, Timestamp date2);
    /*Retrieve payments by an order identifier and payment method */
    @Query("SELECT p FROM Payment p WHERE p.order_id = ?1 AND p.payment_method = ?2")
    List<Payment> findByOrderIdAndPaymentMethod(Long orderId, String paymentMethod);
}
