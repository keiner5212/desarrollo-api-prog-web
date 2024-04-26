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
import eshop.prod.database.entities.OrderItem;
import eshop.prod.database.entities.Product;
import eshop.prod.database.repository.models.RepositoryTestParent;

@SuppressWarnings("null")
class OrderRepositoryTest extends RepositoryTestParent {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    Customer customer1;
    Order order1;
    Order order2;

    @BeforeEach
    void setUpBeforeClass() {
        customer1 = customerRepository.save(Customer.builder()
                .name("customer1")
                .email("email1")
                .address("address1")
                .password("password")
                .role("USER")
                .build());
        order1 = Order.builder()
                .customer_id(customer1)
                .order_date(new Timestamp(System.currentTimeMillis() + 10000))
                .status("PENDIENTE")
                .build();

        order2 = Order.builder()
                .customer_id(customer1)
                .order_date(new Timestamp(System.currentTimeMillis() + 20000))
                .status("ENVIADO")
                .build();
    }

    @AfterEach
    void setUp() {
        orderItemRepository.flush();
        orderItemRepository.deleteAll();
        productRepository.flush();
        productRepository.deleteAll();
        orderRepository.flush();
        orderRepository.deleteAll();
        customerRepository.flush();
        customerRepository.deleteAll();
    }

    // crud

    // create
    @Test
    void testSave() {
        Order saved = orderRepository.save(order1);
        Order found = orderRepository.findAll().get(0);
        assertEquals(saved, found);
    }

    // read
    @Test
    void testFindAll() {
        orderRepository.save(order1);
        orderRepository.save(order2);
        assertEquals(2, orderRepository.count());
    }

    // read by id
    @Test
    void testFindById() {
        Order saved = orderRepository.save(order1);
        Order found = orderRepository.findById(saved.getId_order()).get();
        assertEquals(saved, found);
    }

    // delete
    @Test
    void testDeleteById() {
        Order saved = orderRepository.save(order1);
        assertEquals(1, orderRepository.count());
        orderRepository.deleteById(saved.getId_order());
        assertEquals(0, orderRepository.count());
    }

    // aditional
    @Test
    void testFindByCustomerId() {
        Order saved = orderRepository.save(order1);
        Order found = orderRepository.findAllOrderByCustomerId(customer1.getId_customer()).get().get(0);
        assertEquals(saved, found);
    }

    @Test
    void testFindByDateBetween() {
        orderRepository.save(order1);
        orderRepository.save(order2);
        List<Order> res = orderRepository.findByDateBetween(new Timestamp(System.currentTimeMillis() - 10000),
                new Timestamp(System.currentTimeMillis() + 30000)).get();
        assertEquals(2, res.size());
    }

    @Test
    void testFindByCustomerAndStatus() {
        orderRepository.save(order1);
        orderRepository.save(order2);
        assertEquals(1, orderRepository.findByCustomerAndStatus(customer1.getId_customer(), "PENDIENTE").get().size());
    }

    @Test
    void testFindByIdWithOrderItems() {
        orderRepository.save(order1);
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

        orderItemRepository.save(orderItem1);
        List<OrderItem> res = orderRepository.findByIdWithOrderItems(order1.getId_order()).get();
        assertEquals(1, res.size());
    }
}
