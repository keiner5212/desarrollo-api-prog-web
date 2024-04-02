package eshop.prod.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Product;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
    /* search for products based on a search term */
    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE lower(concat('%', ?1, '%')) OR lower(p.description) LIKE lower(concat('%', ?1, '%')) OR lower(p.brand) LIKE lower(concat('%', ?1, '%'))")
    Optional<List<Product>> findBySearchTerm(String temrm);

    /* Search for products that are in stock. */
    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    Optional<List<Product>> findByStockGreaterThanZero();

    /* Search for products that do not exceed a certain price and stock */
    @Query("SELECT p FROM Product p WHERE p.price <= ?1 AND p.stock <= ?2")
    Optional<List<Product>> findByPriceAndStockLessThan(double price, double stock);
}
