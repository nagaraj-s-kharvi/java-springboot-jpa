package com.data.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.data.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	//Fetch list of Orders by Customer ID, using custom JPA method
	List<Order> findByCustomerId(Long customerId);
	/*
	 * Other Alternate ways
    //Achieving the same outcome is possible through the utilization of JPQL (Java Persistence Query Language)
    @Query("SELECT o FROM Order o WHERE o.customerId = :customerId")
    List<Order> findByCustomerId(@Param("customerId") Long customerId);
    
    @Query("SELECT o FROM Order o WHERE o.customerId = :customerId")
	List<Order> findByCustomerId(Long customerId);
	
	//Achieving the same outcome is possible through the utilization of Native Queries (SQL Query)
	@Query(value = "SELECT o.* FROM orders o WHERE o.customer_id = :customerId", nativeQuery = true) 
	List<Order> findByCustomerId(Long customerId); 
    
    @Query(value = "SELECT * FROM orders WHERE customer_id = :customerId", nativeQuery = true) 
	List<Order> findByCustomerId(Long customerId); 
    */
	
	//Retrieve a list of orders by customer IDs using the IN clause. 
	List<Order> findByCustomerIdIn(Set<Long> customerIds);
	
	/*
	 * Other Alternate ways
	//Achieving the same outcome is possible through the utilization of JPQL (Java Persistence Query Language)
	@Query("SELECT o FROM Order o WHERE o.customerId IN (:customerIds)") 
	List<Order> findByCustomerIdIn(Set<Long> customerIds);
	
	//Achieving the same outcome is possible through the utilization of Native Queries (SQL Query)
	@Query(value = "SELECT o.* FROM orders o WHERE o.customer_id IN (:customerIds)", nativeQuery = true) 
	List<Order> findByCustomerIdIn(Set<Long> customerIds);
	
	@Query(value = "SELECT * FROM orders WHERE customer_id IN (:customerId)", nativeQuery = true) 
	List<Order> findByCustomerIdIn(Set<Long> customerIds); 
	*/
	
	//Retrieve a list of orders by customer IDs with some operators (NOTIN, LESSTHAN, LESSTHANEQUAL, GREATERTHAN, GREATERTHANEQUAL). 
	//Retrieve orders that were not placed by specific customer IDs  
	List<Order> findByCustomerIdNotIn(@Param("customerIds") Set<Long> customerIds);
	
	//Retrieve orders that were placed before the specified date   
	List<Order> findByCustomerIdInAndOrderDateLessThan(@Param("customerIds") Set<Long> customerIds, @Param("orderDate") LocalDate orderDate); 
	
	//Retrieve orders that were placed after the specified date 
	List<Order> findByCustomerIdInAndOrderDateGreaterThan(@Param("customerIds") Set<Long> customerIds, @Param("orderDate") LocalDate orderDate);
	
	//Retrieve orders that were placed before or equal to the specified date 
	List<Order> findByCustomerIdInAndOrderDateLessThanEqual(@Param("customerIds") Set<Long> customerIds, @Param("orderDate") LocalDate orderDate);
	
	//Retrieve orders that were placed after or equal to the specified date 
	List<Order> findByCustomerIdInAndOrderDateGreaterThanEqual(@Param("customerIds") Set<Long> customerIds, @Param("orderDate") LocalDate orderDate); 
	
	
	//Retrieve a list of orders by customer IDs using the IN clause and sorting the results. 
	//Fetch Orders by customer ids and order by order id in ascending order using JPA   
	List<Order> findByCustomerIdInOrderByOrderIdAsc(Set<Long> customerIds); 

	//Fetch Orders by customer ids and order by order id in descending order using JPA 
	List<Order> findByCustomerIdInOrderByOrderIdDesc(Set<Long> customerIds); 

	//Fetch Orders by customer ids and order by order id in descending and order date ascending order using JPA 
	List<Order> findByCustomerIdInOrderByOrderIdDescOrderDateAsc(Set<Long> customerIds); 

	/**
	//Fetch Orders by customer ids and order by order id in desc order using Sort Object 
	List<Order> orders = orderRepository.findByCustomerIdIn(Set.of(1l, 2l), Sort.by("orderId").ascending()); 
	
	List<Order> findByCustomerIdIn(Set<Long> customerIds, Sort sort); 
	*/
	
	/**
	//Fetch Orders by customer ids and order by order id in descending and order date ascending using Sort Object 
	List<Order> orders = orderRepository.findByCustomerIdIn(Set.of(1l, 2l), Sort.by(Sort.Order.desc("orderId"), Sort.Order.asc("orderDate"))); 
	
	List<Order> findByCustomerIdIn(Set<Long> customerIds, Sort sort); 
	*/

	/**
	//Fetch Orders by customer ids and order by order id in descending and order date ascending using JPQL 
	List<Order> orders = orderRepository.findByCustomerIdIn(Set.of(1l, 2l)); 
	
	@Query(value = "SELECT o FROM Order o WHERE o.customerId IN (:customerIds) ORDER BY orderId DESC, orderDate ASC") 
	List<Order> findByCustomerIdIn(Set<Long> customerIds);
	*/

	/**
	//Fetch Orders by customer ids and order by order id in descending and order date ascending using Native Query 
	List<Order> orders = orderRepository.findByCustomerIdIn(Set.of(1l, 2l)); 

	@Query(value = "SELECT o.* FROM orders o WHERE o.customer_id IN (:customerIds) ORDER BY order_id desc, order_date asc", nativeQuery = true) 
	List<Order> findByCustomerIdIn(Set<Long> customerIds); 
	*/
	
	//Fetch Top 5 Orders filter by customer ids using JPA 
	List<Order> findTop5ByCustomerIdIn(Set<Long> customerIds); 
	
	/**
	 * Achieving the same outcome is possible through the utilization of JPQL (Java Persistence Query Language)
	Page = 0 
	Size = 5 
	List<Order> orders = orderRepository.findByCustomerIdInOffsetAndLimit(Set.of(1l, 2l), PageRequest.of(0, 5)); 
	*/
	 
	@Query("SELECT o FROM Order o WHERE o.customerId IN (:customerIds)") 
	List<Order> findByCustomerIdInOffsetAndLimit(Set<Long> customerIds, Pageable pageable); 

	/**
	Page = 0 
	Size = 5 
	List<Order> orders = orderRepository.findByCustomerIdInOffsetAndLimit(Set.of(1l, 2l), 0, 5); 
	*/
	@Query(value = "SELECT * FROM orders WHERE customer_id IN (:customerIds) OFFSET :offset LIMIT :size", nativeQuery = true) 
	List<Order> findByCustomerIdInOffsetAndLimit(Set<Long> customerIds, int offset, int size);
	
	//Retrieve a list of orders by customer IDs using the IN, LIKE clause, and narrow down the records by filtering for orders placed on the 17th day. 
	@Query("SELECT o FROM Order o WHERE o.customerId IN :customerIds AND TO_CHAR(o.orderDate, 'YYYY-MM-DD') LIKE :day") 
	List<Order> findByCustomerIdInAndDay(Set<Long> customerIds, String day); 

}