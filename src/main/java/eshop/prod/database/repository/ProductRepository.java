package eshop.prod.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Product;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
    /* search for products based on a search term */
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> findByNameContaining(String name);

    /* Search for products that are in stock. */
    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<Product> findByStockGreaterThanZero();

    /* Search for products that do not exceed a certain price and stock */
    @Query("SELECT p FROM Product p WHERE p.price <= ?1 AND p.stock > 0")
    List<Product> findByPriceLessThanAndStockGreaterThan(Double price);
}
