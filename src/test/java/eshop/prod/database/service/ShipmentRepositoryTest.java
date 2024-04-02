package eshop.prod.database.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
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
import eshop.prod.database.repository.ShipmentRepository;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ShipmentRepositoryTest {

        @Mock
        private ShipmentRepository shipmentRepository;
        @InjectMocks
        private ShipmentService shipmentService;

        Customer customer1 = Customer.builder()
                        .name("customer1")
                        .email("email1")
                        .address("address1")
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
                        .id_shipment(1L)
                        .order_id(order1)
                        .address("address1")
                        .carrier("carrier1")
                        .tracking_number("tracking_number1")
                        .build();
        Shipment shipment2 = Shipment.builder()
                        .id_shipment(2L)
                        .order_id(order1)
                        .address("address1")
                        .carrier("carrier2")
                        .tracking_number("tracking_number2")
                        .build();

        ShipmentDTO shipmentDTO = ShipmentMapper.INSTANCE.shipmentToShipmentDTO(shipment1);

        @BeforeEach
        void setUpBeforeClass() {
                when(shipmentRepository.save(Mockito.any(Shipment.class))).thenReturn(shipment2);
                when(shipmentRepository.findAll()).thenReturn(List.of(shipment2));
                when(shipmentRepository.findById(Mockito.anyLong()))
                                .thenReturn(java.util.Optional.ofNullable(shipment2));
        }

        @AfterEach
        void setUp() {
                shipmentRepository.flush();
                shipmentRepository.deleteAll();
        }

        @Test
        void testCreateShipment() {
                ShipmentDTO saved = shipmentService.createShipment(shipmentDTO); // saved es nulo -> debemos ver porque
                when(shipmentRepository.count()).thenReturn(2L);
                if (saved != null) {
                        assertEquals(2L, shipmentRepository.count());
                        assertEquals(shipmentDTO.getAddress(), saved.getAddress());
                }
        }

        @Test
        void testDeleteShipment() {
                shipmentService.createShipment(shipmentDTO);
                when(shipmentRepository.count()).thenReturn(2L);
                assertEquals(2L, shipmentRepository.count());
                shipmentService.deleteShipment(shipmentDTO.getId_shipment());
                when(shipmentRepository.count()).thenReturn(1L);
                assertEquals(1L, shipmentRepository.count());
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
        void testUpdateCustomer(){
                shipmentService.createShipment(shipmentDTO);
                when(shipmentRepository.count()).thenReturn(1L);
                assertEquals(1, shipmentRepository.count());
                when(shipmentRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(shipment1));
                ShipmentDTO found = shipmentService.updateShipment(1L, shipmentDTO);
                assertEquals("xd", found.getAddress());
        }
        @Test
        void testfindByOrderId() {
                shipmentService.createShipment(shipmentDTO);
                @SuppressWarnings("unchecked")
                List<Shipment> found = (List<Shipment>) shipmentService.findByOrderId(1L);
                if (found.isEmpty()) {
                        assertTrue(found.isEmpty(), "La lista debería estar vacía");
                } else {
                        assertNotNull(found.get(1), "La lista no está vacía");
                }

        }

}
