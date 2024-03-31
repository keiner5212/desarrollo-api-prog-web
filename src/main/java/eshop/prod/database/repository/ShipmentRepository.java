package eshop.prod.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Shipment;

@RepositoryRestResource
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    /* Find shipping details by order ID */
    @Query("SELECT s FROM Shipment s WHERE s.id_order = ?1")
    List<Shipment> findByOrderId(Long orderId);

    /* Find shipping details for a carrier */
    @Query("SELECT s FROM Shipment s WHERE s.tracking_number LIKE %?1%")
    List<Shipment> findByCarrier(String nameCarrier);

    /* Find shipping details by state */
    @Query("SELECT s FROM Shipment s WHERE s.order_id.state LIKE %?1%")
    List<Shipment> findByState(String state);

}
