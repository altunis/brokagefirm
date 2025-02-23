package brokagefirm.service;

import java.beans.JavaBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import brokagefirm.model.Customer;
import brokagefirm.model.Orders;
import brokagefirm.repository.CustomerRepository;
import brokagefirm.repository.OrderRepository;

@Component("authService")
public class AuthorizationService {
	
	@Autowired
	public CustomerRepository customerRepository;
	
	@Autowired
	public OrderRepository orderRepository;
	
	public boolean userHasAuthorization(Long customerId) {
		
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		boolean result = false;
		if (username.equalsIgnoreCase("admin") ) {
			return true;
		};
		if (customerId == null ) {
			return false;
		}
		Customer customer = customerRepository.getCustomerByName(username);
		if (customer.getId() == customerId) {
			result = true;
		}
		return result;
	}
	
	public boolean userHasAuthorizationForOrder(Long orderId) {
		
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		boolean result = false;
		if (username.equalsIgnoreCase("admin") ) {
			return true;
		};
		if (orderId == null ) {
			return false;
		}
		Customer customer = customerRepository.getCustomerByName(username);
		Orders order = orderRepository.getReferenceById(orderId);
		if (order.getCustomer().getId() == customer.getId()) {
			result = true;
		}
		return result;
	}
}
