package eshop.prod.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Customer;

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    /** find by email */
    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    Customer findByEmail(String email);

    /** find by address */
    @Query("SELECT c FROM Customer c WHERE c.address = ?1")
    List<Customer> findByAddress(String address);

    /** find by name (start like) */
    @Query("SELECT c FROM Customer c WHERE lower(c.name) like lower(concat(?1, '%'))")
    List<Customer> findByNameStartingWith(String name);
}
