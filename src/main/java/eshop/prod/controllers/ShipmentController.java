package eshop.prod.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eshop.prod.database.entities.dto.ShipmentDTO;
import eshop.prod.database.repository.OrderRepository;
import eshop.prod.database.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/shipments/{id}")
    public ResponseEntity<HashMap<String, Object>> getShipmentById(@PathVariable("id") Long id) {
        log.info("Getting shipment by id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        ShipmentDTO data = shipmentService.getShipmentById(id);
        if (data == null) {
            response.put("error", "Shipment not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/shipments")
    public ResponseEntity<HashMap<String, Object>> getShipments() {
        log.info("Getting all shipments");
        HashMap<String, Object> response = new HashMap<>();
        List<ShipmentDTO> data = shipmentService.getAllShipments();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/shipments/order/{id}")
    public ResponseEntity<HashMap<String, Object>> getShipmentsByOrder(@PathVariable("id") Long id) {
        log.info("Getting shipments by order id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        List<ShipmentDTO> data = shipmentService.findByOrderId(id);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/shipments/order/{nameCarrier}")
    public ResponseEntity<HashMap<String, Object>> getShipmentsByCarrier(
            @PathVariable("nameCarrier") String nameCarrier) {
        log.info("Getting shipments by carrier name: " + nameCarrier);
        HashMap<String, Object> response = new HashMap<>();
        List<ShipmentDTO> data = shipmentService.findBycarrier(nameCarrier);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/shipments")
    public ResponseEntity<HashMap<String, Object>> createShipment(@RequestBody ShipmentDTO shipmentDTO,
            OrderRepository orderRepository) {
        log.info("Creating shipment: " + shipmentDTO);
        HashMap<String, Object> response = new HashMap<>();
        ShipmentDTO data = shipmentService.createShipment(shipmentDTO, orderRepository);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/shipments")
    public ResponseEntity<HashMap<String, Object>> updateShipment(@RequestBody ShipmentDTO shipmentDTO,
            OrderRepository orderRepository) {
        log.info("Updating shipment: " + shipmentDTO);
        HashMap<String, Object> response = new HashMap<>();
        ShipmentDTO data = shipmentService.updateShipment(shipmentDTO, orderRepository);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/shipments/order/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteShipmentByOrderId(@PathVariable("id") Long id) {
        log.info("Deleting shipment by order id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        shipmentService.deleteShipment(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
