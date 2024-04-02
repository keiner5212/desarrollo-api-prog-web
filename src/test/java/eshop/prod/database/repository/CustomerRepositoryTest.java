package eshop.prod.database.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eshop.prod.database.entities.Customer;
import eshop.prod.database.repository.models.RepositoryTestParent;

@SuppressWarnings("null")
class CustomerRepositoryTest extends RepositoryTestParent {
    @Autowired
    private CustomerRepository customerRepository;

    Customer customer1;
    Customer customer2;

    @BeforeEach
    void setUpBeforeClass() {
        customer1=Customer.builder()
        .name("customer1")
        .email("email1")
        .address("address1")
        .build();

        customer2=Customer.builder()
        .name("customer2")
        .email("email2")
        .address("address2")
        .build();
    }
    
    @AfterEach
    void setUp() {
        customerRepository.flush();
        customerRepository.deleteAll();
    }

    //crud

    //create
    @Test
    void testSave() {
        Customer saved = customerRepository.save(customer1);
        Customer found = customerRepository.findAll().get(0);
        assertEquals(saved, found);
    }

    //read
    @Test
    void testFindAll() {
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        assertEquals(2, customerRepository.count());
    }

    //read by id
    @Test
    void testFindById() {
        Customer saved = customerRepository.save(customer1);
        Customer found = customerRepository.findById(saved.getId_customer()).get();
        assertEquals(saved, found);
    }

    //update
    @Test
    void testUpdate() {
        Customer saved = customerRepository.save(customer1);
        saved.setAddress("address2");
        Customer updated = customerRepository.save(saved);
        assertEquals("address2", updated.getAddress());
    }

    //delete
    @Test
    void testDeleteById() {
        Customer saved = customerRepository.save(customer1);
        assertEquals(1, customerRepository.count());
        customerRepository.deleteById(saved.getId_customer());
        assertEquals(0, customerRepository.count());
    }

    //aditionals
    @Test
    void testFindByEmail() {
        Customer saved = customerRepository.save(customer1);
        Customer found = customerRepository.findByEmail("email1").get();
        assertEquals(saved, found);
    }

    @Test
    void testfindByAddress() {
        customerRepository.save(customer1);
        List<Customer> found = customerRepository.findByAddress("address1").orElse(null);
        assertEquals(1, found.size());
    }

    @Test
    void testFindByNameStartingWith() {
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        List<Customer> found = customerRepository.findByNameStartingWith("cust").orElse(null);
        assertEquals(2, found.size());
    }

}
