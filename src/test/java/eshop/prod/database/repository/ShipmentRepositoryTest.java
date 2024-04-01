package eshop.prod.database.repository;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eshop.prod.database.entities.Customer;
import eshop.prod.database.entities.Order;
import eshop.prod.database.entities.Shipment;
import eshop.prod.database.repository.models.RepositoryTestParent;

@SuppressWarnings("null")
class ShipmentRepositoryTest extends RepositoryTestParent {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    Customer customer1;
    Order order1;
    Order order2;
    Shipment shipment1;
    Shipment shipment2;

    @BeforeEach
    void setUpBeforeClass() {
        customer1 = customerRepository.save(Customer.builder()
                .name("customer1")
                .email("email1")
                .address("address1")
                .build());
        order1 = orderRepository.save(Order.builder()
                .customer_id(customer1)
                .order_date(new Timestamp(System.currentTimeMillis() + 10000))
                .status("PENDIENTE")
                .build());

        order2 = orderRepository.save(Order.builder()
                .customer_id(customer1)
                .order_date(new Timestamp(System.currentTimeMillis() + 20000))
                .status("ENVIADO")
                .build());

        shipment1 = Shipment.builder()
                .order_id(order1)
                .address("address1")
                .carrier("carrier1")
                .tracking_number("tracking_number1")
                .build();

        shipment2 = Shipment.builder()
                .order_id(order2)
                .address("address2")
                .carrier("carrier1")
                .tracking_number("tracking_number2")
                .build();
    }

    @AfterEach
    void setUp() {
        shipmentRepository.flush();
        shipmentRepository.deleteAll();
        orderRepository.flush();
        orderRepository.deleteAll();
        customerRepository.flush();
        customerRepository.deleteAll();
    }

    // crud

    @Test
    void testSave() {
        Shipment saved = shipmentRepository.save(shipment1);
        Shipment found = shipmentRepository.findAll().get(0);
        assertEquals(saved, found);
    }

    // read by id
    @Test
    void testFindById() {
        Shipment saved = shipmentRepository.save(shipment1);
        Shipment found = shipmentRepository.findById(saved.getId_shipment()).get();
        assertEquals(saved, found);
    }

    // read
    @Test
    void testFindAll() {
        shipmentRepository.save(shipment1);
        shipmentRepository.save(shipment2);
        assertEquals(2, shipmentRepository.count());
    }

    // update
    @Test
    void testUpdate() {
        Shipment saved = shipmentRepository.save(shipment1);
        saved.setAddress("address2");
        Shipment updated = shipmentRepository.save(saved);
        assertEquals("address2", updated.getAddress());
    }

    // delete
    @Test
    void testDeleteById() {
        shipmentRepository.save(shipment1);
        assertEquals(1, shipmentRepository.count());
        shipmentRepository.deleteById(shipment1.getId_shipment());
        assertEquals(0, shipmentRepository.count());
    }

    // aditional

    @Test
    void testFindByOrder() {
        shipmentRepository.save(shipment1);
        Shipment found = shipmentRepository.findByOrderId(order1.getId_order()).get();
        assertEquals(shipment1.getId_shipment(), found.getId_shipment());
    }

    @Test
    void testFindByCarrier() {
        shipmentRepository.save(shipment1);
        shipmentRepository.save(shipment2);
        List<Shipment> found = shipmentRepository.findByCarrier("carrier1").get();
        assertEquals(2, found.size());
    }

    @Test
    void testFindByStatus() {
        shipmentRepository.save(shipment1);
        shipmentRepository.save(shipment2);
        List<Shipment> found = shipmentRepository.findByStatus("PENDIENTE").get();
        assertEquals(1, found.size());
    }

}
