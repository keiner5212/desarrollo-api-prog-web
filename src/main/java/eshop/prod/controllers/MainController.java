package eshop.prod.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class MainController {
    @GetMapping("/")
    public ResponseEntity<HashMap<String, Object>> index() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Hello World!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
