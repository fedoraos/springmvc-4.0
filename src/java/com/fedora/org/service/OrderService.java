package com.fedora.org.service;

import com.fedora.org.domain.Order;


public interface OrderService {
	void processOrder(String productId, long quantity  );
	
	Long saveOrder(Order order);
}
