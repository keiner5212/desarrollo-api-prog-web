package eshop.prod.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import eshop.prod.database.entities.dto.CustomerDTO;
import java.util.List;

import eshop.prod.database.service.CustomerService;

import java.util.HashMap;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<HashMap<String, Object>> getCustomers() {
        HashMap<String, Object> response = new HashMap<>();
        List<CustomerDTO> customers = customerService.getAllCustomers();
        response.put("customers", customers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
