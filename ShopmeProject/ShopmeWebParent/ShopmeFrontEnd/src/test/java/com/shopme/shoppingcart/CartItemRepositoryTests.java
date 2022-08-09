package com.shopme.shoppingcart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartItemRepositoryTests {
	
	@Autowired private CartItemRepository cartItemRepository;
	@Autowired private TestEntityManager entityManager;
	
//	@Test
//	public void testSaveItem() {
//		
//		Integer customerId = 1;
//		Integer productId = 1;
//		
//		Customer customer = entityManager.find(Customer.class, customerId);
//		Product product = entityManager.find(Product.class, productId);
//		
//		CartItem cartItem = new CartItem();
//		
//		cartItem.setCustomer(customer);   
//		cartItem.setProduct(product);
//		cartItem.setQuantity(1);
//		
//		CartItem savedCartItem = cartItemRepository.save(cartItem);
//		
//		assertThat(savedCartItem.getId()).isGreaterThan(0);
//		
//	}
	
//	@Test
//	public void testSave2Item() {
//		
//		Integer customerId = 10;
//		Integer productId = 10;
//		
//		Customer customer = entityManager.find(Customer.class, customerId);
//		Product product = entityManager.find(Product.class, productId);
//		
//		CartItem item1 = new CartItem();
//		item1.setCustomer(customer);   
//		item1.setProduct(product);
//		item1.setQuantity(2);
//		
//		CartItem item2 = new CartItem();
//		item2.setCustomer(new Customer(customerId));
//		item2.setProduct(new Product(8));
//		item2.setQuantity(3);
//		
//		Iterable<CartItem> iterable = cartItemRepository.saveAll(List.of(item1, item2));
//		
//		assertThat(iterable).size().isGreaterThan(0);
//		
//	}
	
//	@Test
//	public void testFindByCustomer() {
//		Integer customerId = 10;
//		Customer customer = entityManager.find(Customer.class, customerId);
//		
//		List<CartItem> listItems = cartItemRepository.findByCustomer(customer);
//		
//		listItems.forEach(System.out :: println);
//		
//		assertThat(listItems.size()).isEqualTo(2);
//	}
	
//	@Test
//	public void testFindByCustomerAndProduct() {
//		Customer customer = new Customer(10);
//		Product product = new Product(10);
//		
//		CartItem cartItem = cartItemRepository.findByCustomerAndProduct(customer, product);
//		
//		assertThat(cartItem).isNotNull();
//		
//		System.out.println(cartItem);
//	}
	
//	@Test
//	public void testUpdateQuantity() {
//		Integer customerId = 1;
//		Integer productId = 1;
//		int quantity = 4;
//		
//		cartItemRepository.updateQuantity(customerId, productId, quantity);
//		
//		CartItem cartItem = cartItemRepository.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
//		
//		assertThat(cartItem.getQuantity()).isEqualTo(4);
//		
//		System.out.println(cartItem);
//		
//	}
	
	@Test
	public void testDeleteByCustomerAndProduct() {
		Integer customerId = 10;
		Integer productId = 10;
		
		cartItemRepository.deleteByCustomerAndProduct(customerId, productId);
		
		CartItem cartItem = cartItemRepository.findByCustomerAndProduct(new Customer(customerId), new Product(productId));
		
		assertThat(cartItem).isNull();
	}

}
