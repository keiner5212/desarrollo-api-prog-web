package eshop.prod.database.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

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
import eshop.prod.database.entities.dto.OrderDTO;
import eshop.prod.database.entities.dto.OrderItemDTO;
import eshop.prod.database.entities.mappers.OrderMapper;
import eshop.prod.database.repository.CustomerRepository;
import eshop.prod.database.repository.OrderRepository;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceTest {

        @Mock
        private OrderRepository orderRepository;

        @Mock
        private CustomerRepository customerRepository;

        @InjectMocks
        private OrderService orderService;

        Customer customer = Customer.builder()
                        .id_customer(1L)
                        .name("customer1")
                        .email("email1")
                        .address("address1")
                        .password("password")
                        .build();
        Order order1 = Order.builder()
                        .customer_id(customer)
                        .order_date(new Timestamp(System.currentTimeMillis() + 10000))
                        .status("PENDIENTE")
                        .build();
        Order orderres = Order.builder()
                        .id_order(2L)
                        .customer_id(customer)
                        .order_date(new Timestamp(System.currentTimeMillis() + 10000))
                        .status("COMPLETADO")
                        .build();

        OrderDTO orderDTO = OrderMapper.INSTANCE.orderToOrderDTO(order1);

        @BeforeEach
        void setUpBeforeClass() {
                when(orderRepository.save(Mockito.any(Order.class))).thenReturn(orderres);
                when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(orderres));
                when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
        }

        @Test
        void testCreateOrder() {
                OrderDTO saved = orderService.createOrder(orderDTO);
                assertNotNull(saved);
                assertEquals(orderDTO.getOrder_date(), saved.getOrder_date());
        }

        @Test
        void testDeleteOrder() {
                OrderDTO saved = orderService.createOrder(orderDTO);
                when(orderRepository.count()).thenReturn(1L);
                assertEquals(1L, orderRepository.count());
                orderService.deleteOrder(saved.getId_order());
                when(orderRepository.count()).thenReturn(0L);
                assertEquals(0L, orderRepository.count());
        }

        @Test
        void testFindAllOrderByCustomerId() {
                orderService.createOrder(orderDTO);
                when(orderRepository.findAllOrderByCustomerId(Mockito.anyLong()))
                .thenReturn(Optional.of(List.of(orderres)));
                List<OrderDTO> list = orderService.findAllOrderByCustomerId(customer.getId_customer());
                assertNotNull(list);
                assertEquals(1, list.size());
        }

        @Test
        void testFindByCustomerAndStatus() {
                orderService.createOrder(orderDTO);
                when(orderRepository.findByCustomerAndStatus(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(Optional.of(List.of(orderres)));
                List<OrderDTO> list = orderService.findByCustomerAndStatus(customer.getId_customer(), "PENDIENTE");
                assertNotNull(list);
                assertEquals(1, list.size());
        }

        @Test
        void testFindByDate() {
                orderService.createOrder(orderDTO);
                when(orderRepository.findByDateBetween(Mockito.any(Timestamp.class), Mockito.any(Timestamp.class)))
                .thenReturn(Optional.of(List.of(orderres)));
                List<OrderDTO> list = orderService.findByDate(new Timestamp(System.currentTimeMillis() - 10000),
                                new Timestamp(System.currentTimeMillis() + 30000));
                assertNotNull(list);
                assertEquals(1, list.size());
        }

        @Test
        void testFindByIdWithOrderItems() {
                orderService.createOrder(orderDTO);
                when(orderRepository.findByIdWithOrderItems(Mockito.anyLong())).thenReturn(Optional.of(List.of()));
                List<OrderItemDTO> list = orderService.findByIdWithOrderItems(orderDTO.getId_order());
                assertNotNull(list);
                assertEquals(0, list.size());
        }

        @Test
        void testGetAllOrders() {
                orderService.createOrder(orderDTO);
                when(orderRepository.findAll()).thenReturn(List.of(orderres));
                List<OrderDTO> list = orderService.getAllOrders();
                assertNotNull(list);
                assertEquals(1, list.size());
        }

        @Test
        void testGetOrderById() {
                orderService.createOrder(orderDTO);
                OrderDTO order = orderService.getOrderById(1L); 
                assertNotNull(order);
                assertEquals(orderDTO.getCustomer_id(), order.getCustomer_id());
        }

        @Test
        void testUpdateOrder() {
                OrderDTO saved = orderService.createOrder(orderDTO);
                saved.setStatus("COMPLETADO");
                OrderDTO order = orderService.updateOrder(1L,saved);
                assertNotNull(order);
                assertEquals(saved.getStatus(), order.getStatus());
        }
}
