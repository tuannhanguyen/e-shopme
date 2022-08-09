package com.shopme.shoppingcart;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Product;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
	
	public List<CartItem> findByCustomer(Customer customer);
	
	public CartItem findByCustomerAndProduct(Customer customer, Product product);
	
	
	@Modifying
	@Query("UPDATE CartItem c SET c.quantity = ?3 WHERE c.customer.id = ?1 AND c.product.id = ?2")
	public void updateQuantity(Integer customerId, Integer productId, int quantity);
	
	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.customer.id = ?1 AND c.product.id = ?2 ")
	public void deleteByCustomerAndProduct(Integer customerId, Integer productId);

}
