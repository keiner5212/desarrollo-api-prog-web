package eshop.prod.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import eshop.prod.database.entities.dto.CustomerDTO;
import eshop.prod.database.service.CustomerService;
import eshop.prod.security.JWTGenerator;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    CustomerService customerService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTGenerator jwtGenerator;

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, Object>> createCustomer(@RequestBody CustomerDTO customerDTO) {
        log.info("Creating customer: " + customerDTO);
        HashMap<String, Object> response = new HashMap<>();
        String encryptedPassword = passwordEncoder.encode(customerDTO.getPassword());
        customerDTO.setPassword(encryptedPassword);
        CustomerDTO res = customerService.createCustomer(customerDTO);
        if (res == null) {
            response.put("error", "Error creating customer");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("data", "Customer created");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> authenticateUser(@RequestBody CustomerDTO customerDTO) {

        CustomerDTO existingCustomer = customerService.getCustomerByEmail(customerDTO.getEmail());

        if (existingCustomer == null
                || !passwordEncoder.matches(customerDTO.getPassword(), existingCustomer.getPassword())) {
            HashMap<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                existingCustomer.getEmail(),
                existingCustomer.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtGenerator.generateToken(authentication);

        HashMap<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }

}
