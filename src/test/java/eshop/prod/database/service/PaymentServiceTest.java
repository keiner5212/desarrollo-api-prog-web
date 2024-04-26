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
import eshop.prod.database.entities.Payment;
import eshop.prod.database.entities.dto.PaymentDTO;
import eshop.prod.database.entities.mappers.PaymentMapper;
import eshop.prod.database.repository.CustomerRepository;
import eshop.prod.database.repository.OrderRepository;
import eshop.prod.database.repository.PaymentRepository;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PaymentServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    
    Customer customer=Customer.builder()
            .id_customer(1L)
            .name("customer1")
            .email("email1")
            .address("address1")
            .password("password")
            .role("USER")
            .build();
    Order order=Order.builder()
            .id_order(1L)
            .customer_id(customer)
            .order_date(new Timestamp(System.currentTimeMillis() + 10000))
            .status("PENDIENTE")
            .build();

    Payment payment=Payment.builder()
            .order_id(order)
            .amount(10.0)
            .payment_date(new Timestamp(System.currentTimeMillis() + 10000))
            .payment_method("PAYPAL")
            .build();

    Payment paymentRes=Payment.builder()
            .id_payment(1L)
            .order_id(order)
            .amount(10.0)
            .payment_date(new Timestamp(System.currentTimeMillis() + 10000))
            .payment_method("PAYPAL")
            .build();

    PaymentDTO paymentDTO=PaymentMapper.INSTANCE.paymentToPaymentDTO(payment);

    @BeforeEach
    void setUpBeforeClass() {
        when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(paymentRes);
        when(paymentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(paymentRes));
        when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(customer));
    }

    @Test
    void testCreatePayment() {
        PaymentDTO saved = paymentService.createPayment(paymentDTO);
        assertNotNull(saved);
        assertEquals(paymentDTO.getPayment_date(), saved.getPayment_date());
    }

    @Test
    void testDeletePayment() {
        PaymentDTO saved = paymentService.createPayment(paymentDTO);
        when(paymentRepository.count()).thenReturn(1L);
        assertEquals(1L, paymentRepository.count());
        paymentService.deletePayment(saved.getId_payment());
        when(paymentRepository.count()).thenReturn(0L);
        assertEquals(0L, paymentRepository.count());
    }

    @Test
    void testFindByDate() {
        PaymentDTO saved = paymentService.createPayment(paymentDTO);
        when(paymentRepository.findByDateBetween(Mockito.any(Timestamp.class), Mockito.any(Timestamp.class)))
        .thenReturn(Optional.of(List.of(paymentRes)));
        List<PaymentDTO> list = paymentService.findByDate(saved.getPayment_date(), saved.getPayment_date());
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void testFindByOrderIdAndPaymentMethod() {
        PaymentDTO saved = paymentService.createPayment(paymentDTO);
        when(paymentRepository.findByOrderIdAndPaymentMethod(Mockito.anyLong(), Mockito.anyString()))
        .thenReturn(Optional.of(paymentRes));
        PaymentDTO found = paymentService
                .findByOrderIdAndPaymentMethod(order.getId_order(), saved.getPayment_method());
        assertNotNull(found);
    }

    @Test
    void testGetAllPayments() {
        paymentService.createPayment(paymentDTO);
        when(paymentRepository.findAll()).thenReturn(List.of(paymentRes));
        List<PaymentDTO> list = paymentService.getAllPayments();
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void testGetPaymentById() {
        PaymentDTO saved = paymentService.createPayment(paymentDTO);
        when(paymentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(paymentRes));
        PaymentDTO found = paymentService.getPaymentById(saved.getId_payment());
        assertNotNull(found);
    }

    @Test
    void testUpdatePayment() {
        PaymentDTO saved = paymentService.createPayment(paymentDTO);
        when(paymentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(paymentRes));
        when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(paymentRes);
        PaymentDTO updated = paymentService.updatePayment(saved.getId_payment(), saved);
        assertNotNull(updated);
        assertEquals(paymentDTO.getPayment_date(), updated.getPayment_date());
    }
}
