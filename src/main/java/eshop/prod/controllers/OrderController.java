package eshop.prod.controllers;

import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eshop.prod.database.entities.dto.OrderDTO;
import eshop.prod.database.service.OrderService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("") // GET ALL
    public ResponseEntity<HashMap<String, Object>> getOrders() {
        log.info("Getting all orders");
        HashMap<String, Object> response = new HashMap<>();
        List<OrderDTO> data = orderService.getAllOrders();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}") // get ID
    public ResponseEntity<HashMap<String, Object>> getOrderById(@PathVariable("id") Long id) {
        log.info("Getting order by id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        OrderDTO data = orderService.getOrderById(id);
        if (data == null) {
            response.put("error", "Order not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{customer_id}") // GET PEDIDO POR CLIENTE ID
    public ResponseEntity<HashMap<String, Object>> getOrdersByCustomer(@PathVariable("customer_id") Long customer_id) {
        log.info("Getting orders by customer id: " + customer_id);
        HashMap<String, Object> response = new HashMap<>();
        List<OrderDTO> data = orderService.findAllOrderByCustomerId(customer_id);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/date-range") // GET PEDIDOS por fecha
    public ResponseEntity<HashMap<String, Object>> getByDate(
            @RequestParam("startDate") Timestamp startDate, // "yyyy-MM-dd 23:59:59"
            @RequestParam("endDate") Timestamp endDate // "yyyy-MM-dd hh:mm:ss"
    ) {
        HashMap<String, Object> response = new HashMap<>();
        List<OrderDTO> data = orderService.findByDate(startDate, endDate);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}/status/{status}") // GET PEDIDOS por estado
    public ResponseEntity<HashMap<String, Object>> getByStatus(@PathVariable("id") Long id,
            @PathVariable("status") String status) {
        HashMap<String, Object> response = new HashMap<>();
        List<OrderDTO> data = orderService.findByCustomerAndStatus(id, status);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("") // POST
    public ResponseEntity<HashMap<String, Object>> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("Creating order");
        HashMap<String, Object> response = new HashMap<>();
        OrderDTO data = orderService.createOrder(orderDTO);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // PUT
    public ResponseEntity<HashMap<String, Object>> updateOrder(
            @PathVariable("id") Long id, @RequestBody OrderDTO orderDTO) {
        log.info("Updating order");
        HashMap<String, Object> response = new HashMap<>();
        OrderDTO data = orderService.updateOrder(id, orderDTO);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // DELETE
    public ResponseEntity<HashMap<String, Object>> deleteOrder(@PathVariable("id") Long id) {
        log.info("Deleting order");
        HashMap<String, Object> response = new HashMap<>();
        orderService.deleteOrder(id);
        response.put("message", "Order deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
