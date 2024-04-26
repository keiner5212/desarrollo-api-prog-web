package eshop.prod.database.service;

import static org.junit.Assert.*;
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
import eshop.prod.database.entities.OrderItem;
import eshop.prod.database.entities.Product;
import eshop.prod.database.entities.dto.OrderItemDTO;
import eshop.prod.database.entities.mappers.OrderItemMapper;
import eshop.prod.database.repository.CustomerRepository;
import eshop.prod.database.repository.OrderItemRepository;
import eshop.prod.database.repository.OrderRepository;
import eshop.prod.database.repository.ProductRepository;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderItemService orderItemService;

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

    Product product1 = Product.builder()
            .id_product(1L)
            .name("product1")
            .price(1.0)
            .description("description1")
            .brand("brand1")
            .image("image1")
            .stock(10)
            .build();

    OrderItem orderItem1 = OrderItem.builder()
            .order_id(order1)
            .product_id(product1)
            .quantity(5)
            .unit_price(1.0)
            .build();

    OrderItem orderItemRes = OrderItem.builder()
            .id_order_item(1L)
            .order_id(order1)
            .product_id(product1)
            .quantity(5)
            .unit_price(1.0)
            .build();

    OrderItem orderItemRes2 = OrderItem.builder()
            .id_order_item(1L)
            .order_id(order1)
            .product_id(product1)
            .quantity(10)
            .unit_price(1.0)
            .build();

    OrderItemDTO orderItemDTO = OrderItemMapper.INSTANCE.orderItemToOrderItemDTO(orderItem1);

    @BeforeEach
    void setUpBeforeClass() {
        when(orderItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(orderItemRes));
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product1));
        when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(order1));
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customer1));
    }

    @Test
    void testCreateOrderItem() {
        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(orderItemRes);
        when(orderItemRepository.count()).thenReturn(1L);

        OrderItemDTO saved = orderItemService.createOrderItem(orderItemDTO);
        assertEquals(1L, orderItemRepository.count());
        assertEquals(orderItemDTO.getUnit_price(), saved.getUnit_price());
    }

    @Test
    void testDeleteOrderItem() {
        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(orderItemRes);
        OrderItemDTO saved = orderItemService.createOrderItem(orderItemDTO);
        when(orderItemRepository.count()).thenReturn(1L);
        assertEquals(1L, orderItemRepository.count());
        orderItemService.deleteOrderItem(saved.getId_order_item());
        when(orderItemRepository.count()).thenReturn(0L);
        assertEquals(0L, orderItemRepository.count());
    }

    @Test
    void testGetAllOrderItems() {
        orderItemService.createOrderItem(orderItemDTO);
        orderItemService.createOrderItem(orderItemDTO);
        when(orderItemRepository.findAll()).thenReturn(List.of(orderItemRes, orderItemRes));
        assertEquals(2, orderItemService.getAllOrderItems().size());
    }

    @Test
    void testGetOrderItemById() {
        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(orderItemRes);
        OrderItemDTO orderItemDTOSaved = orderItemService.createOrderItem(orderItemDTO);
        when(orderItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(orderItemRes));
        assertEquals(orderItemDTO.getUnit_price(), orderItemDTOSaved.getUnit_price());
    }

    @Test
    void testGetOrderItemsByOrderId() {
        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(orderItemRes);
        orderItemService.createOrderItem(orderItemDTO);
        when(orderItemRepository.findByOrderId(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(List.of(orderItemRes)));
        assertEquals(1, orderItemService.getOrderItemsByOrderId(1L).size());
    }

    @Test
    void testGetOrderItemsByProductId() {
        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(orderItemRes);
        orderItemService.createOrderItem(orderItemDTO);
        when(orderItemRepository.findByProductId(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(List.of(orderItemRes)));
        assertEquals(1, orderItemService.getOrderItemsByProductId(1L).size());
    }

    @Test
    void testGetTotalSellByProductId() {
        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(orderItemRes);
        orderItemService.createOrderItem(orderItemDTO);
        when(orderItemRepository.findTotalSellByProductId(Mockito.anyLong())).thenReturn(Optional.ofNullable(5));
        int total = orderItemService.getTotalSellByProductId(1L);
        assertEquals(5, total);
    }

    @Test
    void testUpdateOrderItem() {
        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(orderItemRes);
        OrderItemDTO orderItemDTOSaved = orderItemService.createOrderItem(orderItemDTO);
        orderItemDTOSaved.setQuantity(10);
        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(orderItemRes2);
        OrderItemDTO updated = orderItemService.updateOrderItem(1L, orderItemDTO);
        assertEquals(10, updated.getQuantity().intValue());
    }
}
