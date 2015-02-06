package com.fedora.org.repository;

import com.fedora.org.domain.Cart;


public interface CartRepository {

	Cart create(Cart cart);
	
	Cart read(String cartId);
	
	void update(String cartId, Cart cart);
	
	void delete(String cartId);

}
