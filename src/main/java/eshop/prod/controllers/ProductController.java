package eshop.prod.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import eshop.prod.database.entities.dto.ProductDTO;
import eshop.prod.database.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getProducts() {
        log.info("Getting all products");
        HashMap<String, Object> response = new HashMap<>();
        List<ProductDTO> data = productService.getAllProducts();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getProductById(@PathVariable("id") Long id) {
        log.info("Getting product by id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        ProductDTO data = productService.getProductById(id);
        if (data == null) {
            response.put("error", "Product not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search/{seacrh}")
    public ResponseEntity<HashMap<String, Object>> getProductsBySearch(@PathVariable("seacrh") String seacrh) {
        log.info("Getting products by search: " + seacrh);
        HashMap<String, Object> response = new HashMap<>();
        List<ProductDTO> data = productService.findByNameContaining(seacrh);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/price-stock/{price}/{stock}")
    public ResponseEntity<HashMap<String, Object>> getProductsByPriceAndStock(@PathVariable("price") Double price,
            @PathVariable("stock") Double stock) {
        log.info("Getting products by price and stock: " + price + " " + stock);
        HashMap<String, Object> response = new HashMap<>();
        List<ProductDTO> data = productService.findByPriceLessThanAndStockGreaterThan(price, stock);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/stock")
    public ResponseEntity<HashMap<String, Object>> getProductsByStock() {
        log.info("Getting products by stock");
        HashMap<String, Object> response = new HashMap<>();
        List<ProductDTO> data = productService.findByStockGreaterThanZero();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> createProduct(@RequestBody ProductDTO productDTO) {
        log.info("Creating product: " + productDTO);
        HashMap<String, Object> response = new HashMap<>();
        ProductDTO data = productService.createProduct(productDTO);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updateProduct(@PathVariable("id") Long id,
            @RequestBody ProductDTO productDTO) {
        log.info("Updating product: " + productDTO);
        HashMap<String, Object> response = new HashMap<>();
        ProductDTO data = productService.updateProduct(id, productDTO);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteProduct(@PathVariable("id") Long id) {
        log.info("Deleting product by id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        productService.deleteProduct(id);
        response.put("message", "Product deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
