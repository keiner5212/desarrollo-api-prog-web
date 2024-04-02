package eshop.prod.database.entities.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import eshop.prod.database.entities.Order;
import eshop.prod.database.entities.dto.OrderDTO;
import eshop.prod.database.repository.CustomerRepository;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customer_id.id_customer", target = "customer_id")
    public OrderDTO orderToOrderDTO(Order order);
    default Order orderDTOToOrder(
        OrderDTO orderDTO,
        CustomerRepository customerRepository
    ){
        if (orderDTO == null) {
            return null;
        }
        Order res = new Order();
        res.setId_order(orderDTO.getId_order());
        long id=orderDTO.getCustomer_id();
        res.setCustomer_id(customerRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Customer not found with id: " + orderDTO.getCustomer_id())
        ));
        res.setOrder_date(orderDTO.getOrder_date());
        res.setStatus(orderDTO.getStatus());
        return res;
    }
}
