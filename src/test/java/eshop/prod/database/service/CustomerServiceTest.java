package eshop.prod.database.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import eshop.prod.database.entities.Customer;
import eshop.prod.database.entities.dto.CustomerDTO;
import eshop.prod.database.entities.mappers.CustomerMapper;
import eshop.prod.database.repository.CustomerRepository;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    Customer customer1 = Customer.builder()
            .name("customer1")
            .email("email1")
            .address("address1")
            .password("password")
            .role("USER")
            .build();
    Customer customerRes = Customer.builder()
            .id_customer(1L)
            .name("customer1")
            .email("xd")
            .address("address1")
            .password("password")
            .role("USER")
            .build();

    CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDTO(customer1);

    @BeforeEach
    void setUpBeforeClass() {
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customerRes);
        when(customerRepository.findAll()).thenReturn(List.of(customerRes));
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customerRes));
    }

    @Test
    void testCreateCustomer() {
        CustomerDTO saved = customerService.createCustomer(customerDTO);
        when(customerRepository.count()).thenReturn(1L);
        assertEquals(1L, customerRepository.count());
        assertEquals(customerDTO.getName(), saved.getName());
    }

    @Test
    void testDeleteCustomer() {
        CustomerDTO saved = customerService.createCustomer(customerDTO);
        when(customerRepository.count()).thenReturn(1L);
        assertEquals(1L, customerRepository.count());
        customerService.deleteCustomer(saved.getId_customer());
        when(customerRepository.count()).thenReturn(0L);
        assertEquals(0L, customerRepository.count());
    }

    @Test
    void testGetAllCustomers() {
        customerService.createCustomer(customerDTO);
        customerService.createCustomer(customerDTO);
        when(customerRepository.findAll()).thenReturn(List.of(customerRes, customerRes));
        assertEquals(2, customerService.getAllCustomers().size());
    }

    @Test
    void testGetCustomerByAddress() {
        when(customerRepository.findByAddress(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(List.of(customerRes)));
        customerService.createCustomer(customerDTO);
        when(customerRepository.count()).thenReturn(1L);
        assertEquals(1, customerService.getCustomerByAddress("address1").size());
    }

    @Test
    void testGetCustomerByEmail() {
        when(customerRepository.findByEmail(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(customerRes));
        customerService.createCustomer(customerDTO);
        assertEquals(customerDTO.getName(),
                customerService.getCustomerByEmail("email1").getName());
    }

    @Test
    void testGetCustomerById() {
        customerService.createCustomer(customerDTO);
        CustomerDTO found = customerService.getCustomerById(1L);
        assertEquals(customerDTO.getName(), found.getName());
    }

    @Test
    void testGetCustomerByNameStartingWith() {
        when(customerRepository.findByNameStartingWith(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(List.of(customerRes, customerRes)));
        customerService.createCustomer(customerDTO);
        assertEquals(2,
                customerService.getCustomerByNameStartingWith("cust").size());
    }

    @Test
    void testUpdateCustomer() {
        customerService.createCustomer(customerDTO);
        when(customerRepository.count()).thenReturn(1L);
        assertEquals(1, customerRepository.count());
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customer1));
        CustomerDTO found = customerService.updateCustomer(1L, customerDTO);
        assertEquals("xd", found.getEmail());
    }
}
