package eshop.prod.database.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import eshop.prod.database.entities.Payment;
import eshop.prod.database.repository.PaymentRepository;

class PaymentServiceTest {
    @Mock
    PaymentRepository paymentRepository;
    @InjectMocks
    PaymentService paymentService;

    Payment payment1 = Payment.builder()
            .id_payment(1L)
            .amount(0.0)
            .build();

}
