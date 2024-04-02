package eshop.prod.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Shipment;

@RepositoryRestResource
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    /* Find shipping details by order ID */
    @Query("SELECT s FROM Shipment s join fetch s.order_id o WHERE o.id_order = ?1")
    Optional<Shipment> findByOrderId(long orderId);

    /* Find shipping details for a carrier */
    @Query("SELECT s FROM Shipment s WHERE lower(s.carrier) LIKE lower(concat('%',?1, '%'))")
    Optional<List<Shipment>> findByCarrier(String nameCarrier);

    /* Find shipping details by state */
    @Query("SELECT s FROM Shipment s join fetch s.order_id o WHERE lower(o.status) LIKE lower(concat('%', ?1, '%'))")
    Optional<List<Shipment>> findByStatus(String state);

}
