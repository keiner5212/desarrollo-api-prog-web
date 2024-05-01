package eshop.prod.controllers;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import eshop.prod.controllers.docs.Endpoints;

@RestController
public class MainController {
    @GetMapping("/")
    public ResponseEntity<HashMap<String, Object>> index() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Hello World!");
        response.put("info", "Welcome to our API! to use it visit /api/v1");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/v1")
    public ResponseEntity<HashMap<String, Object>> index2() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to our API!");
        response.put("info",
                "To use any endpoint, visit /api/v1/{endpoint} (first need to authenticate, see auth");

        HashMap<String, Object> z_endpoints = new HashMap<>();
        z_endpoints.put("Endpoints docs", "GET /api/v1");
        z_endpoints.put("Auth", Endpoints.AUTH_ENDPOINTS);
        z_endpoints.put("Products", Endpoints.PRODUCT_ENDPOINTS);
        z_endpoints.put("Customers", Endpoints.CUSTOMERS_ENDPOINTS);
        z_endpoints.put("Orders", Endpoints.ORDER_ENDPOINTS);
        z_endpoints.put("Order Items", Endpoints.ORDER_ITEM_ENDPOINTS);
        z_endpoints.put("Payments", Endpoints.PAYMENT_ENDPOINTS);
        z_endpoints.put("Shipments", Endpoints.SHIPMENT_ENDPOINTS);

        response.put("Endpoints", z_endpoints);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
