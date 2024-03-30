package eshop.prod.controllers;

import java.util.HashMap;
import java.util.List;
import eshop.prod.database.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import eshop.prod.database.entities.dto.OrderDTO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
public class OrderController {
    /*
     * @GetMapping("/order-items")
    public ResponseEntity<HashMap<String, Object>> getOrderItems(){
        log.info("Getting all order items");
        HashMap<String, Object> response = new HashMap<>();
        List<OrderItemDTO> data = orderItemService.getAllOrderItems();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
     */
   /* @GetMapping("/orders")
    public ResponseEntity<HashMap<String, Object>> getOrders(){
        log.info("Getting all orders");
        HashMap<String, Object> response = new HashMap<>();
        List<OrderDTO> data = OrderService.getAllOrd
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/ 

}
