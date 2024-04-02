package eshop.prod.database.entities.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import eshop.prod.database.entities.OrderItem;
import eshop.prod.database.entities.dto.OrderItemDTO;
import eshop.prod.database.repository.OrderRepository;
import eshop.prod.database.repository.ProductRepository;

@Mapper
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(source = "order_id.id_order", target = "order_id")
    @Mapping(source = "product_id.id_product", target = "product_id")
    public OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);

    default OrderItem orderItemDTOToOrderItem(OrderItemDTO orderItemDTO,
            OrderRepository orderRepository,
            ProductRepository productRepository) {
        if (orderItemDTO == null) {
            return null;
        }

        OrderItem res = new OrderItem();
        long id = orderItemDTO.getProduct_id();
        res.setProduct_id(productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found with id: " + orderItemDTO.getProduct_id())));
        res.setQuantity(orderItemDTO.getQuantity());
        res.setUnit_price(orderItemDTO.getUnit_price());
        id = orderItemDTO.getOrder_id();
        res.setOrder_id(orderRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Order not found with id: " + orderItemDTO.getOrder_id())));

        return res;
    }
}
