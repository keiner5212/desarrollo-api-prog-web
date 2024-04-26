package eshop.prod.database.repository;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eshop.prod.database.entities.Customer;
import eshop.prod.database.entities.Order;
import eshop.prod.database.entities.OrderItem;
import eshop.prod.database.entities.Product;
import eshop.prod.database.repository.models.RepositoryTestParent;

@SuppressWarnings("null")
class OrderItemRepositoryTest extends RepositoryTestParent {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    Customer customer1;
    Order order1;
    Product product1;
    OrderItem orderItem1;
    OrderItem orderItem2;

    @BeforeEach
    void setUpBeforeClass() {
        customer1 = customerRepository.save(Customer.builder()
                .name("customer1")
                .email("email1")
                .address("address1")
                .password("password")
                .role("USER")
                .build());
        order1 = orderRepository.save(Order.builder()
                .customer_id(customer1)
                .order_date(new Timestamp(System.currentTimeMillis() + 10000))
                .status("PENDIENTE")
                .build());
        product1 = productRepository.save(Product.builder()
                .name("product1")
                .price(1.0)
                .description("description1")
                .brand("brand1")
                .image("image1")
                .stock(10)
                .build());

        orderItem1 = OrderItem.builder()
                .order_id(order1)
                .product_id(product1)
                .quantity(1)
                .unit_price(1.0)
                .build();
        orderItem2 = OrderItem.builder()
                .order_id(order1)
                .product_id(product1)
                .quantity(5)
                .unit_price(1.0)
                .build();

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

    // crud

    // create
    @Test
    void testSave() {
        OrderItem saved = orderItemRepository.save(orderItem1);
        OrderItem found = orderItemRepository.findAll().get(0);
        assertEquals(saved, found);
    }

    // read
    @Test
    void testFindAll() {
        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);
        assertEquals(2, orderItemRepository.count());
        assertEquals(6, orderItemRepository.findTotalSellByProductId(product1.getId_product()).get().intValue());
    }

    // read by id
    @Test
    void testFindById() {
        OrderItem saved = orderItemRepository.save(orderItem1);
        OrderItem found = orderItemRepository.findById(saved.getId_order_item()).get();
        assertEquals(saved, found);
    }

    // update
    @Test
    void testUpdate() {
        OrderItem saved = orderItemRepository.save(orderItem1);
        saved.setQuantity(2);
        OrderItem updated = orderItemRepository.save(saved);
        assertEquals(2, updated.getQuantity().intValue());
    }

    // delete
    @Test
    void testDeleteById() {
        OrderItem saved = orderItemRepository.save(orderItem1);
        assertEquals(1, orderItemRepository.count());
        orderItemRepository.deleteById(saved.getId_order_item());
        assertEquals(0, orderItemRepository.count());
    }

    // aditionals
    @Test
    void testFindByOrderId() {
        OrderItem saved = orderItemRepository.save(orderItem1);
        OrderItem found = orderItemRepository.findByOrderId(saved.getOrder_id().getId_order()).get().get(0);
        assertEquals(saved, found);
    }

    @Test
    void testFindByProductId() {
        OrderItem saved = orderItemRepository.save(orderItem1);
        OrderItem found = orderItemRepository.findByProductId(saved.getProduct_id().getId_product()).get().get(0);
        assertEquals(saved, found);
    }

    @Test
    void testFindTotalSellByProductId() {
        OrderItem saved = orderItemRepository.save(orderItem1);
        Integer found = orderItemRepository.findTotalSellByProductId(saved.getProduct_id().getId_product()).get();
        assertEquals(1, found.intValue());
    }

}
