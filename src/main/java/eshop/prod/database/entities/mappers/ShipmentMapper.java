package eshop.prod.database.entities.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import eshop.prod.database.entities.Shipment;
import eshop.prod.database.entities.dto.ShipmentDTO;
import eshop.prod.database.repository.OrderRepository;

@Mapper
public interface ShipmentMapper {
    ShipmentMapper INSTANCE = Mappers.getMapper(ShipmentMapper.class);

    @Mapping(source = "order_id.id_order", target = "order_id")
    public ShipmentDTO shipmentToShipmentDTO(Shipment shipment);
    default Shipment shipmentDTOToShipment(
        ShipmentDTO shipmentDTO,
        OrderRepository orderRepository
        ){
        if (shipmentDTO == null) {
            return null;
        }

        Shipment res = new Shipment();
        res.setId_shipment(shipmentDTO.getId_shipment());
        long id=shipmentDTO.getOrder_id();
        res.setOrder_id(orderRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Order not found with id: " + shipmentDTO.getOrder_id())
        ));
        res.setAddress(shipmentDTO.getAddress());
        res.setCarrier(shipmentDTO.getCarrier());
        res.setTracking_number(shipmentDTO.getTracking_number());
        return res;
    }
}
