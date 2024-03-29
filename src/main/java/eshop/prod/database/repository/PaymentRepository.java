package eshop.prod.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eshop.prod.database.entities.Payment;

@RepositoryRestResource
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}
