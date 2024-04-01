package eshop.prod.database.entities.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import eshop.prod.database.entities.Payment;
import eshop.prod.database.entities.dto.PaymentDTO;
import eshop.prod.database.repository.OrderRepository;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(source = "order_id.id_order", target = "order_id")
    public PaymentDTO paymentToPaymentDTO(Payment payment);
    default Payment paymentDTOToPayment(
        PaymentDTO paymentDTO,
        OrderRepository orderRepository
        ){
        if (paymentDTO == null) {
            return null;
        }

        Payment res = new Payment();
        res.setId_payment(paymentDTO.getId_payment());
        long id=paymentDTO.getOrder_id();
        res.setOrder_id(orderRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Order not found with id: " + paymentDTO.getOrder_id())
        ));
        res.setAmount(paymentDTO.getAmount());
        res.setPayment_date(paymentDTO.getPayment_date());
        res.setPayment_method(paymentDTO.getPayment_method());
        return res;
    }
}
