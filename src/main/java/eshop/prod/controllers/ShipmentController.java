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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eshop.prod.database.entities.dto.ShipmentDTO;
import eshop.prod.database.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/shipments")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/{id}")
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

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getShipments() {
        log.info("Getting all shipments");
        HashMap<String, Object> response = new HashMap<>();
        List<ShipmentDTO> data = shipmentService.getAllShipments();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<HashMap<String, Object>> getShipmentsByOrder(@PathVariable("id") Long id) {
        log.info("Getting shipments by order id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        ShipmentDTO data = shipmentService.findByOrderId(id);
        if (data == null) {
            response.put("error", "Shipment not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/carrier/{nameCarrier}")
    public ResponseEntity<HashMap<String, Object>> getShipmentsByCarrier(
            @PathVariable("nameCarrier") String nameCarrier) {
        log.info("Getting shipments by carrier name: " + nameCarrier);
        HashMap<String, Object> response = new HashMap<>();
        List<ShipmentDTO> data = shipmentService.findBycarrier(nameCarrier);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> createShipment(@RequestBody ShipmentDTO shipmentDTO) {
        log.info("Creating shipment: " + shipmentDTO);
        HashMap<String, Object> response = new HashMap<>();
        ShipmentDTO data = shipmentService.createShipment(shipmentDTO);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<HashMap<String, Object>> updateShipment(@PathVariable("id") Long id,
            @RequestBody ShipmentDTO shipmentDTO) {
        log.info("Updating shipment: " + shipmentDTO);
        HashMap<String, Object> response = new HashMap<>();
        ShipmentDTO data = shipmentService.updateShipment(id, shipmentDTO);
        if (data == null) {
            response.put("error", "error updating shipment");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteShipmentByOrderId(@PathVariable("id") Long id) {
        log.info("Deleting shipment by order id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        shipmentService.deleteShipment(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
