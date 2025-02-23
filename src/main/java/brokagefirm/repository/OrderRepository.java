package brokagefirm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import brokagefirm.model.Customer;
import brokagefirm.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomerAndStatus(Customer customer, String status);
}