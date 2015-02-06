package com.fedora.org.repository;

import com.fedora.org.domain.Order;


public interface OrderRepository {
	Long saveOrder(Order order);
}
