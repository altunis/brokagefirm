package brokagefirm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import brokagefirm.dto.CustomerDto;
import brokagefirm.model.Customer;
import brokagefirm.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PreAuthorize("@authService.userHasAuthorization(#id)")
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PreAuthorize("@authService.userHasAuthorization(#id)")
    @RequestMapping(method =  RequestMethod.GET,value = {"", "/{id}"})
    public ResponseEntity<List<CustomerDto>> getAllCustomers(@PathVariable(required = false) Long id) {
    	return ResponseEntity.ok(customerService.getCustomerById(id));
       
    }
}