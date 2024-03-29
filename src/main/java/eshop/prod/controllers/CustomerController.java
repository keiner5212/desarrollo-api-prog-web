package eshop.prod.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import eshop.prod.database.entities.dto.CustomerDTO;
import java.util.List;

import eshop.prod.database.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@Slf4j
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<HashMap<String, Object>> getCustomers() {
        log.info("Getting all customers");
        HashMap<String, Object> response = new HashMap<>();
        List<CustomerDTO> customers = customerService.getAllCustomers();
        response.put("customers", customers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<HashMap<String, Object>> getCustomerById(@PathVariable("id") Long id) {
        log.info("Getting customer by id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        CustomerDTO customer = customerService.getCustomerById(id);
        if (customer == null) {
            response.put("error", "Customer not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("customer", customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customers/email/{email}")
    public ResponseEntity<HashMap<String, Object>> getCustomerByEmail(@PathVariable("email") String email) {
        log.info("Getting customer by email: " + email);
        HashMap<String, Object> response = new HashMap<>();
        CustomerDTO customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            response.put("error", "Customer not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("customer", customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customers/address/{address}")
    public ResponseEntity<HashMap<String, Object>> getCustomerByAddress(@PathVariable("address") String address) {
        log.info("Getting customer by address: " + address);
        HashMap<String, Object> response = new HashMap<>();
        List<CustomerDTO> customers = customerService.getCustomerByAddress(address);
        response.put("customers", customers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/customers/name/{name}")
    public ResponseEntity<HashMap<String, Object>> getCustomerByName(@PathVariable("name") String name) {
        log.info("Getting customer by name: " + name);
        HashMap<String, Object> response = new HashMap<>();
        List<CustomerDTO> customers = customerService.getCustomerByNameStartingWith(name);
        response.put("customers", customers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity<HashMap<String, Object>> createCustomer(@RequestBody CustomerDTO customerDTO) {
        log.info("Creating customer: " + customerDTO);
        HashMap<String, Object> response = new HashMap<>();
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        response.put("customer", createdCustomer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<HashMap<String, Object>> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
        log.info("Updating customer: " + customerDTO);
        HashMap<String, Object> response = new HashMap<>();
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        if (updatedCustomer == null) {
            response.put("error", "Error updating customer");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("customer", updatedCustomer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteCustomer(@PathVariable("id") Long id) {
        log.info("Deleting customer with id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        if (customerService.deleteCustomer(id)) {
            response.put("message", "Customer deleted successfully");
        } else {
            response.put("error", "Error deleting customer");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
