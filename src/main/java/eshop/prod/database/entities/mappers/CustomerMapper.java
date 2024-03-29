package eshop.prod.database.entities.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import eshop.prod.database.entities.Customer;
import eshop.prod.database.entities.dto.CustomerDTO;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    
    public CustomerDTO customerToCustomerDTO(Customer customer);
    public Customer customerDTOToCustomer(CustomerDTO customerDTO);
    
}
