package eshop.prod.database.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import eshop.prod.database.entities.Customer;
import eshop.prod.database.entities.Order;
import eshop.prod.database.entities.OrderItem;
import eshop.prod.database.entities.Product;
import eshop.prod.database.entities.dto.OrderItemDTO;
import eshop.prod.database.entities.mappers.OrderItemMapper;
import eshop.prod.database.repository.CustomerRepository;
import eshop.prod.database.repository.OrderItemRepository;
import eshop.prod.database.repository.OrderRepository;
import eshop.prod.database.repository.ProductRepository;

class OrderItemRepositoryTest {

    @Mock
    private OrderItemRepository orderItemRepository;
    @InjectMocks
    private OrderItemService orderItemService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    Customer customer1 = customerRepository.save(Customer.builder()
            .name("customer1")
            .email("email1")
            .address("address1")
            .build());
    Order order1 = orderRepository.save(Order.builder()
            .customer_id(customer1)
            .order_date(new Timestamp(System.currentTimeMillis() + 10000))
            .status("PENDIENTE")
            .build());
    Product product1 = productRepository.save(Product.builder()
            .name("product1")
            .price(1.0)
            .description("description1")
            .brand("brand1")
            .image("image1")
            .stock(10)
            .build());

    OrderItem orderItem1 = OrderItem.builder()
            .order_id(order1)
            .product_id(product1)
            .quantity(1)
            .unit_price(1.0)
            .build();
    OrderItem orderItem2 = OrderItem.builder()
            .id_order_item(1L)
            .order_id(order1)
            .product_id(product1)
            .quantity(5)
            .unit_price(1.0)
            .build();
            OrderItemDTO orderItemDTO = OrderItemMapper.INSTANCE.orderItemToOrderItemDTO(orderItem1);

    @BeforeEach
    void setUpBeforeClass() {
        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(orderItem2);
        when(orderItemRepository.findAll()).thenReturn(List.of(orderItem2));
        when(orderItemRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(orderItem2));
    }
    @AfterEach
    void setUp() {
        orderItemRepository.flush();
        orderItemRepository.deleteAll();

        orderRepository.flush();
        orderRepository.deleteAll();

        customerRepository.flush();
        customerRepository.deleteAll();

        productRepository.flush();
        productRepository.deleteAll();
    }
    @Test
    void testCreateRepositoryTest() {
        OrderItemDTO saved = orderItemService.createOrderItem(orderItemDTO);
        orderItemRepository.count();
        when(orderItemRepository.count()).thenReturn(orderItemDTO.getId_order_item());
        assertEquals(1L, orderItemRepository.count());
        assertEquals(orderItemDTO.getUnit_price(), saved.getUnit_price());
    }
}
