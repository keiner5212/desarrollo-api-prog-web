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
import org.springframework.web.bind.annotation.RestController;

import eshop.prod.database.entities.dto.PaymentDTO;
import eshop.prod.database.repository.OrderRepository;
import eshop.prod.database.service.PaymentService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments") // get all
    public ResponseEntity<HashMap<String, Object>> getPayments() {
        log.info("Getting all payment items");
        HashMap<String, Object> response = new HashMap<>();
        List<PaymentDTO> data = paymentService.getAllPayments();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/payments/{id}") // get id
    public ResponseEntity<HashMap<String, Object>> getPaymentById(@PathVariable("id") Long id) {
        log.info("Getting payment item by id: " + id);
        PaymentDTO data = paymentService.getPaymentById(id);
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/payments/order/{id}") // get orderID
    public ResponseEntity<HashMap<String, Object>> getPaymentByOrderId(@PathVariable("id") Long id,
            @PathVariable("paymentMethod") String paymentMethod) {
        log.info("Getting payment item by order id: " + id);
        List<PaymentDTO> data = paymentService.findByOrderIdAndPaymentMethod(id, paymentMethod);
        HashMap<String, Object> response = new HashMap<>();
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/payments/date/{date}") // get date
    public ResponseEntity<HashMap<String, Object>> getPaymentByDate(@PathVariable("date") Timestamp date1,
            Timestamp date2) {
        log.info("Getting payment item by date[" + date1 + "," + date2 + "]");
        List<PaymentDTO> data = paymentService.findByDate(date1, date2);
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", data);
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/payments") // create
    public ResponseEntity<HashMap<String, Object>> createPayment(@RequestBody PaymentDTO paymentDTO,
            OrderRepository orderRepository) {
        log.info("Creating payment: " + paymentDTO);
        HashMap<String, Object> response = new HashMap<>();
        PaymentDTO data = paymentService.createPayment(paymentDTO, orderRepository);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/payments/{id}") // update
    public ResponseEntity<HashMap<String, Object>> updatePayment(@PathVariable("id") OrderRepository orderRepository,
            @RequestBody PaymentDTO paymentDTO) {
        log.info("Updating payment item: " + paymentDTO.getId_payment());
        HashMap<String, Object> response = new HashMap<>();
        PaymentDTO data = paymentService.updatePayment(paymentDTO, orderRepository);
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity<HashMap<String, Object>> deletePayment(@PathVariable("id") Long id) {
        log.info("Deleting payment item with id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        paymentService.deletePayment(id);
        response.put("message", "Payment deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
