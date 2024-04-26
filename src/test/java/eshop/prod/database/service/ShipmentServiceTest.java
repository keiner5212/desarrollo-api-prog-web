package eshop.prod.database.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import eshop.prod.database.entities.Customer;
import eshop.prod.database.entities.Order;
import eshop.prod.database.entities.Shipment;
import eshop.prod.database.entities.dto.ShipmentDTO;
import eshop.prod.database.entities.mappers.ShipmentMapper;
import eshop.prod.database.repository.CustomerRepository;
import eshop.prod.database.repository.OrderRepository;
import eshop.prod.database.repository.ShipmentRepository;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ShipmentServiceTest {
    @Mock
    private ShipmentRepository shipmentRepository;
    
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ShipmentService shipmentService;

    Customer customer1 = Customer.builder()
            .id_customer(1L)
            .name("customer1")
            .email("email1")
            .address("address1")
            .password("password")
            .role("USER")
            .build();
    Order order1 = Order.builder()
            .id_order(1L)
            .customer_id(customer1)
            .order_date(new Timestamp(System.currentTimeMillis() + 10000))
            .status("PENDIENTE")
            .build();
    Order order2 = Order.builder()
            .id_order(2L)
            .customer_id(customer1)
            .order_date(new Timestamp(System.currentTimeMillis() + 10000))
            .status("PENDIENTE")
            .build();

    Shipment shipment1 = Shipment.builder()
            .order_id(order1)
            .address("xd")
            .carrier("carrier1")
            .tracking_number("tracking_number1")
            .build();
    Shipment shipment2 = Shipment.builder()
            .id_shipment(2L)
            .order_id(order1)
            .address("address1")
            .carrier("carrier1")
            .tracking_number("tracking_number2")
            .build();

    ShipmentDTO shipmentDTO = ShipmentMapper.INSTANCE.shipmentToShipmentDTO(shipment1);

    @BeforeEach
    void setUpBeforeClass() {
        when(shipmentRepository.save(Mockito.any(Shipment.class))).thenReturn(shipment2);
        when(shipmentRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(shipment2));
        when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(order1));
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customer1));
    }

    @Test
    void testCreateShipment() {
        ShipmentDTO saved = shipmentService.createShipment(shipmentDTO);
        when(shipmentRepository.count()).thenReturn(1L);
        assertEquals(1L, shipmentRepository.count());
        assertNotNull(saved);
        assertEquals(shipmentDTO.getCarrier(), saved.getCarrier());
    }

    @Test
    void testDeleteShipment() {
        ShipmentDTO saved = shipmentService.createShipment(shipmentDTO);
        when(shipmentRepository.count()).thenReturn(1L);
        assertEquals(1L, shipmentRepository.count());
        shipmentService.deleteShipment(saved.getId_shipment());
        when(shipmentRepository.count()).thenReturn(0L);
        assertEquals(0L, shipmentRepository.count());
    }

    @Test
    void testFindByOrderId() {
        shipmentService.createShipment(shipmentDTO);
        when(shipmentRepository.findByOrderId(Mockito.anyLong())).thenReturn(Optional.ofNullable(shipment2));
        ShipmentDTO found = shipmentService.findByOrderId(1L);
        assertNotNull(found);
        
    }

    @Test
    void testFindByState() {
        shipmentService.createShipment(shipmentDTO);
        when(shipmentRepository.findByStatus(Mockito.anyString())).thenReturn(Optional.ofNullable(List.of(shipment2)));
        List<ShipmentDTO> found = shipmentService.findByState("PENDIENTE");
        assertNotNull(found);
    }

    @Test
    void testFindBycarrier() {
        shipmentService.createShipment(shipmentDTO);
        when(shipmentRepository.findByCarrier(Mockito.anyString())).thenReturn(Optional.ofNullable(List.of(shipment2)));
        List<ShipmentDTO> found = shipmentService.findBycarrier("carrier1");
        assertNotNull(found);
    }

    @Test
    void testGetAllShipments() {
        shipmentService.createShipment(shipmentDTO);
        shipmentService.createShipment(shipmentDTO);
        when(shipmentRepository.findAll()).thenReturn(List.of(shipment2, shipment2));
        assertEquals(2, shipmentService.getAllShipments().size());
    }

    @Test
    void testGetShipmentById() {
        shipmentService.createShipment(shipmentDTO);
        ShipmentDTO found = shipmentService.getShipmentById(1L);
        assertNotNull(found);
    }

    @Test
    void testUpdateShipment() {
        ShipmentDTO saved = shipmentService.createShipment(shipmentDTO);
        when(shipmentRepository.count()).thenReturn(1L);
        assertEquals(1, shipmentRepository.count());
        saved.setAddress("xd");
        when(shipmentRepository.save(Mockito.any(Shipment.class))).thenReturn(shipment2);
        ShipmentDTO updated = shipmentService.updateShipment(1L, shipmentDTO);
        assertEquals("xd", updated.getAddress());
    }
}
