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

import eshop.prod.database.entities.dto.PaymentDTO;
import eshop.prod.database.service.PaymentService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("") // get all
    public ResponseEntity<HashMap<String, Object>> getPayments() {
        log.info("Getting all payment items");
        HashMap<String, Object> response = new HashMap<>();
        List<PaymentDTO> data = paymentService.getAllPayments();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}") // get id
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

    @GetMapping("/order/{id}/payment-method/{paymentMethod}") // get orderID
    public ResponseEntity<HashMap<String, Object>> getPaymentByOrderId(@PathVariable("id") Long id,
            @PathVariable("paymentMethod") String paymentMethod) {
        log.info("Getting payment item by order id: " + id + " and payment method: " + paymentMethod);
        PaymentDTO data = paymentService.findByOrderIdAndPaymentMethod(id, paymentMethod);
        HashMap<String, Object> response = new HashMap<>();
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/date-range") // get date
    public ResponseEntity<HashMap<String, Object>> getPaymentByDate(
            @RequestParam("startDate") Timestamp startDate, // "yyyy-MM-dd 23:59:59"
            @RequestParam("endDate") Timestamp endDate // "yyyy-MM-dd hh:mm:ss"
    ) {
        log.info("Getting payment item by date[" + startDate.toString() + "," + endDate.toString() + "]");
        List<PaymentDTO> data = paymentService.findByDate(startDate, endDate);
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", data);
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("") // create
    public ResponseEntity<HashMap<String, Object>> createPayment(@RequestBody PaymentDTO paymentDTO) {
        log.info("Creating payment: " + paymentDTO);
        HashMap<String, Object> response = new HashMap<>();
        PaymentDTO data = paymentService.createPayment(paymentDTO);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}") // update
    public ResponseEntity<HashMap<String, Object>> updatePayment(@PathVariable("id") Long id,
            @RequestBody PaymentDTO paymentDTO) {
        log.info("Updating payment item: " + paymentDTO.getId_payment());
        HashMap<String, Object> response = new HashMap<>();
        PaymentDTO data = paymentService.updatePayment(id, paymentDTO);
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deletePayment(@PathVariable("id") Long id) {
        log.info("Deleting payment item with id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        paymentService.deletePayment(id);
        response.put("message", "Payment deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
