package eshop.prod.database.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eshop.prod.database.entities.Customer;
import eshop.prod.database.entities.dto.CustomerDTO;
import eshop.prod.database.entities.mappers.CustomerMapper;
import eshop.prod.database.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    /** get all customers */
    public List<CustomerDTO> getAllCustomers() {
        try {
            List<Customer> customers = customerRepository.findAll();
            return customers.stream().map(CustomerMapper.INSTANCE::customerToCustomerDTO).toList();
        } catch (Exception e) {
            log.error("Error getting all customers", e);
        }

        return List.of();
    }

}
