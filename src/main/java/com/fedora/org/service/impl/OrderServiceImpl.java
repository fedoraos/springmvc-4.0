package com.fedora.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedora.org.domain.Order;
import com.fedora.org.domain.Product;
import com.fedora.org.repository.OrderRepository;
import com.fedora.org.repository.ProductRepository;
import com.fedora.org.service.CartService;
import com.fedora.org.service.OrderService;

@Service
public class OrderServiceImpl  implements OrderService{
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public void processOrder(String productId, long quantity) {
		Product pro = productRepository.getProductById(productId);
		if(pro == null){
			throw new IllegalArgumentException("No products found with the product id: "+ productId);
		}
		if(pro.getUnitsInStock() <quantity){
			throw new IllegalArgumentException("Out of Stock. Available Units in stock"+ pro.getUnitsInStock());
		}
		pro.setUnitsInStock(pro.getUnitsInStock());
	}

	public Long saveOrder(Order order) {
		Long orderId = orderRepository.saveOrder(order);
		cartService.delete(order.getCart().getCartId());
		return orderId;
	}

}
