package brokagefirm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import brokagefirm.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	public Customer getCustomerByName(String name);

}