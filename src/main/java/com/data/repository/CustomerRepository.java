package com.data.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.data.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	//Get a list of customers based on order IDs (JOIN). 
	@Query("SELECT c FROM Order o LEFT JOIN Customer c ON o.customerId = c.customerId WHERE o.orderId IN (:orderIds)") 
	List<Customer> findCustomersByOrderIdIn(Set<Long> orderIds); 

	//Retrieve a customer details who ordered last (INNER QUERIES/ NESTED QUERIES) 
	//Retrieve the customer object for the most recent order. 
	@Query("SELECT c FROM Customer c WHERE c.customerId = (SELECT o.customerId FROM Order o ORDER BY orderDate DESC LIMIT 1)") 
	Customer findCustomerWhoOrderedLast();
}
