package eshop.prod.database.service;

import static org.junit.Assert.assertEquals;
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

    Customer customer=Customer.builder()
            .id_customer(1L)
            .name("customer1")
            .email("email1")
            .address("address1")
            .build();
    Order order1=Order.builder()
            .customer_id(customer)
            .order_date(new Timestamp(System.currentTimeMillis() + 10000))
            .status("PENDIENTE")
            .build();
    Order orderres=Order.builder()
            .id_order(2L)
            .customer_id(customer)
            .order_date(new Timestamp(System.currentTimeMillis() + 10000))
            .status("PENDIENTE")
            .build();

    OrderDTO orderDTO=OrderMapper.INSTANCE.orderToOrderDTO(order1);

    @BeforeEach
    void setUpBeforeClass() {
        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(orderres);
        when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(orderres));
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
    }
            
    @Test
    void testCreateOrder() {

    }

    @Test
    void testDeleteOrder() {

    }

    @Test
    void testFindAllOrderByCustomerId() {

    }

    @Test
    void testFindByCustomerAndStatus() {

    }

    @Test
    void testFindByDate() {

    }

    @Test
    void testFindByIdWithOrderItems() {

    }

    @Test
    void testGetAllOrders() {

    }

    @Test
    void testGetOrderById() {

    }

    @Test
    void testUpdateOrder() {

    }
}
