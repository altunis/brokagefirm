package brokagefirm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import brokagefirm.dto.CustomerDto;
import brokagefirm.model.Customer;
import brokagefirm.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public List<CustomerDto> getAllCustomersDto() {
        return customerRepository.findAll().stream().map(e -> e.toDto()).collect(Collectors.toList());
    }

    public List<CustomerDto> getCustomerById(Long id) {
    	List<CustomerDto> result = new ArrayList<CustomerDto>();
    	if (id == null) {
    		return getAllCustomersDto();
    	}
        result.add(customerRepository.getReferenceById(id).toDto());
        return result;
    }
}