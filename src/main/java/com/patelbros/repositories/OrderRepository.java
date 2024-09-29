package com.patelbros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patelbros.entities.Order;
import com.patelbros.entities.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	List<Order> findByUser(User user);
	
	Optional<Order> findByBillNo(String billNo);
	
	Optional<Order> findByPaymentId(String paymentId);
}
