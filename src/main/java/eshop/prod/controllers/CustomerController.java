package eshop.prod.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eshop.prod.database.entities.dto.CustomerDTO;
import eshop.prod.database.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getCustomers() {
        log.info("Getting all customers");
        HashMap<String, Object> response = new HashMap<>();
        List<CustomerDTO> customers = customerService.getAllCustomers();
        response.put("data", customers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getCustomerById(@PathVariable("id") Long id) {
        log.info("Getting customer by id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        CustomerDTO data = customerService.getCustomerById(id);
        if (data == null) {
            response.put("error", "Customer not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<HashMap<String, Object>> getCustomerByEmail(@PathVariable("email") String email) {
        log.info("Getting customer by email: " + email);
        HashMap<String, Object> response = new HashMap<>();
        CustomerDTO data = customerService.getCustomerByEmail(email);
        if (data == null) {
            response.put("error", "Customer not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<HashMap<String, Object>> getCustomerByAddress(@PathVariable("address") String address) {
        log.info("Getting customer by address: " + address);
        HashMap<String, Object> response = new HashMap<>();
        List<CustomerDTO> data = customerService.getCustomerByAddress(address);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<HashMap<String, Object>> getCustomerByName(@PathVariable("name") String name) {
        log.info("Getting customer by name: " + name);
        HashMap<String, Object> response = new HashMap<>();
        List<CustomerDTO> data = customerService.getCustomerByNameStartingWith(name);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updateCustomer(@PathVariable("id") Long id,
            @RequestBody CustomerDTO customerDTO) {
        log.info("Updating customer: " + customerDTO);
        HashMap<String, Object> response = new HashMap<>();
        
        if (customerDTO.getPassword() != null) {
            String encryptedPassword = passwordEncoder.encode(customerDTO.getPassword());
            customerDTO.setPassword(encryptedPassword);
        }

        CustomerDTO data = customerService.updateCustomer(id, customerDTO);
        if (data == null) {
            response.put("error", "Error updating customer");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", "Customer updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteCustomer(@PathVariable("id") Long id) {
        log.info("Deleting customer with id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        if (customerService.deleteCustomer(id)) {
            response.put("data", "Customer deleted successfully");
        } else {
            response.put("error", "Error deleting customer");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
