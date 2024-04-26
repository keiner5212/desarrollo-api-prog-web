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
import eshop.prod.database.entities.Payment;
import eshop.prod.database.repository.models.RepositoryTestParent;

@SuppressWarnings("null")
class PaymentRepositoryTest extends RepositoryTestParent {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    Customer customer1;
    Order order1;
    Payment payment1;

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
            
        payment1 = Payment.builder()
                .order_id(order1)
                .amount(10.0)
                .payment_method("EFECTIVO")
                .payment_date(new Timestamp(System.currentTimeMillis() + 20000))
                .build();
    }

    @AfterEach
    void setUp() {
        paymentRepository.flush();
        paymentRepository.deleteAll();
        orderRepository.flush();
        orderRepository.deleteAll();
        customerRepository.flush();
        customerRepository.deleteAll();
    }

    // crud

    //create
    @Test
    void testSave() {
        paymentRepository.save(payment1);
        Payment found = paymentRepository.findAll().get(0);
        assertEquals(payment1, found);
    }

    // read by id
    @Test
    void testFindById() {
        paymentRepository.save(payment1);
        Payment found = paymentRepository.findById(payment1.getId_payment()).get();
        assertEquals(payment1, found);
    }

    // read all
    @Test
    void testFindAll() {
        paymentRepository.save(payment1);
        assertEquals(1, paymentRepository.count());
    }

    // delete by id
    @Test
    void testDeleteById() {
        paymentRepository.save(payment1);
        assertEquals(1, paymentRepository.count());
        paymentRepository.deleteById(payment1.getId_payment());
        assertEquals(0, paymentRepository.count());
    }

    // delete all
    @Test
    void testDeleteAll() {
        paymentRepository.save(payment1);
        assertEquals(1, paymentRepository.count());
        paymentRepository.deleteAll();
        assertEquals(0, paymentRepository.count());
    }

    // update
    @Test
    void testUpdate() {
        paymentRepository.save(payment1);
        assertEquals(1, paymentRepository.count());
        Payment updated = paymentRepository.findById(payment1.getId_payment()).get();
        updated.setAmount(20.0);
        paymentRepository.save(updated);
        Payment found = paymentRepository.findById(payment1.getId_payment()).get();
        assertEquals(updated, found);
    }

    // aditional
    @Test   
    void testfindByDateBetween()  {
        paymentRepository.save(payment1);
        List<Payment> res = paymentRepository.findByDateBetween(new Timestamp(System.currentTimeMillis() - 10000),
                new Timestamp(System.currentTimeMillis() + 30000)).get();
        assertEquals(1, res.size());
    }

    @Test
    void testFindByOrderIdAndPaymentMethod() {
        paymentRepository.save(payment1);
        Payment res = paymentRepository.findByOrderIdAndPaymentMethod(order1.getId_order(), "EFECTIVO").get();
        assertEquals(payment1.getId_payment(), res.getId_payment());
    }
}
