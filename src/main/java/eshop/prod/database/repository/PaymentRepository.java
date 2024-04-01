package eshop.prod.database.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Payment;

@RepositoryRestResource
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    /* Retrieve payments within a date range */
    @Query("SELECT p FROM Payment p WHERE p.payment_date >= ?1 AND p.payment_date <= ?2")
    Optional<List<Payment>> findByDateBetween(Timestamp date1, Timestamp date2);

    /* Retrieve payments by an order identifier and payment method */
    @Query("SELECT p FROM Payment p join fetch p.order_id o WHERE o.id_order = ?1 AND lower(p.payment_method) = lower(?2)")
    Optional<Payment> findByOrderIdAndPaymentMethod(long orderId, String paymentMethod);
}
