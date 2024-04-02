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

import eshop.prod.database.entities.dto.OrderItemDTO;
import eshop.prod.database.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getOrderItems() {
        log.info("Getting all order items");
        HashMap<String, Object> response = new HashMap<>();
        List<OrderItemDTO> data = orderItemService.getAllOrderItems();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getOrderItemById(@PathVariable("id") Long id) {
        log.info("Getting Order item by id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        OrderItemDTO data = orderItemService.getOrderItemById(id);
        if (data == null) {
            response.put("error", "Order item not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<HashMap<String, Object>> getOrderItemsByOrder(@PathVariable("id") Long id) {
        log.info("Getting Order items by order id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        List<OrderItemDTO> data = orderItemService.getOrderItemsByOrderId(id);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<HashMap<String, Object>> getOrderItemsByProduct(@PathVariable("id") Long id) {
        log.info("Getting Order items by product id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        List<OrderItemDTO> data = orderItemService.getOrderItemsByProductId(id);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/product/{id}/total-sell")
    public ResponseEntity<HashMap<String, Object>> getTotalSellByProduct(@PathVariable("id") Long id) {
        log.info("Getting total sell by product id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        int data = orderItemService.getTotalSellByProductId(id);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        log.info("Creating order item: " + orderItemDTO);
        HashMap<String, Object> response = new HashMap<>();
        OrderItemDTO data = orderItemService.createOrderItem(orderItemDTO);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updateOrderItem(@PathVariable("id") Long id,
            @RequestBody OrderItemDTO orderItemDTO) {
        log.info("Updating order item: " + orderItemDTO);
        HashMap<String, Object> response = new HashMap<>();
        OrderItemDTO data = orderItemService.updateOrderItem(id, orderItemDTO);
        if (data == null) {
            response.put("error", "Error updating order item");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteOrderItem(@PathVariable("id") Long id) {
        log.info("Deleting order item with id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        if (orderItemService.deleteOrderItem(id)) {
            response.put("data", " deleted successfully");
        } else {
            response.put("error", "Error deleting order item");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
