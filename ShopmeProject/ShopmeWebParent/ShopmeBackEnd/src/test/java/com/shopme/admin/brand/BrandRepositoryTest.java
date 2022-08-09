package com.shopme.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTest {
	
	@Autowired
	private BrandRepository repository;
	
//	@Test
//	public void testCreateBrand() {
////		 Brand acer = new Brand("Acer");
//		 Brand apple = new Brand("Apple");
//		 
//		 Category cat1 = new Category(4);
//		 Category cat2 = new Category(7);
//		 
//		 Brand samsung = new Brand("Samsung");
//		 
//		 Category cat3 = new Category(7);
//		 Category cat4 = new Category(3);
//		 
////		 samsung.getCategories().add(cat3);
////		 samsung.getCategories().add(cat4);
//		 
//		 apple.getCategories().add(cat3);
//		 apple.getCategories().add(cat4);
//
//		 Brand savedBrand = repository.save(apple);
//		 
//		 assertThat(savedBrand).isNotNull();
//		 assertThat(savedBrand.getId()).isGreaterThan(0);
//	}
	
//	@Test
//	public void testUpdateBrand() {
//		Brand samsung = repository.findById(7).get();
//		
//		String name = "Samsung";
//		
//		samsung.setName(name);
//		
//		repository.save(samsung);
//	}
	
//	@Test
//	public void testDeleteBrand() {
//		//Brand samsung = repository.findById(6).get();
//		
//		repository.deleteById(6);
//	}
	
	@Test
	public void testFindAll() {
		Iterable<Brand> brands = repository.findAll();
		
		brands.forEach(System.out :: println);
		
		assertThat(brands).isNotEmpty();
	}

}
