package eshop.prod.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Customer;

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    /** find by email */
    @Query("SELECT c FROM Customer c WHERE lower(c.email) = lower(?1)")
    Optional<Customer> findByEmail(String email);

    /** find by address */
    @Query("SELECT c FROM Customer c WHERE lower(c.address) = lower(?1)")
    Optional<List<Customer>> findByAddress(String address);

    /** find by name (start like) */
    @Query("SELECT c FROM Customer c WHERE lower(c.name) like lower(concat(?1, '%'))")
    Optional<List<Customer>> findByNameStartingWith(String name);
}
