package com.shopme.admin.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import com.sun.xml.bind.v2.runtime.reflect.opt.FieldAccessor_Short;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
//	@Test
//	public void testCreateProduct() {
//		Brand brand = testEntityManager.find(Brand.class, 38);
//		Category category = testEntityManager.find(Category.class, 6);
//		
//		Product product = new Product();
//		product.setName("Dell Inspiron 3000");
//		product.setAlias("dell_inspiron_3000");
//		product.setShortDescription("A good laptop from Dell");
//		product.setFullDescription("This is a very good laptop full description");
//		
//		product.setBrand(brand);
//		product.setCategory(category);
//		
//		product.setPrice(789);
//		product.setCost(400);
//		product.setEnabled(true);
//		product.setInStock(true);
//		product.setCreatedDate(new Date());
//		product.setUpdatedDate(new Date());
//		
//		Product savedProduct = repository.save(product);
//		
//		assertThat(savedProduct).isNotNull();
//		assertThat(savedProduct.getId()).isGreaterThan(0);
//	}
	
//	@Test
//	public void testListAllProduct() {
//		Iterable<Product> iterableProduct = repository.findAll();
//		
//		iterableProduct.forEach(System.out :: println );
//	}
	
//	@Test
//	public void testGetProduct() {
//		Integer id = 2;
//		Product product	= repository.findById(id).get();
//		
//		System.out.print(product);
//	}
	
//	@Test
//	public void testUpdateProduct() {
//		Product product = repository.findById(1).get();
//		
//		product.setPrice(499);
//		repository.save(product);
//		
//		Product updatedProduct = testEntityManager.find(Product.class, 1);
//		assertThat(updatedProduct.getPrice()).isEqualTo(499);
//	}
	
//	@Test
//	public void testDeleteProduct() {
//		Integer id = 3;
//		repository.deleteById(id);
//		
//		Optional<Product> product = repository.findById(id);
//		
//		assertThat(!product.isPresent());
//	}
	
//	@Test
//	public void testCreateProductImages() {
//		Product product = repository.findById(14).get();
//		
//		product.setMainImage("main_image");
//		
//		product.addExtraImage("extra image 1");
//		product.addExtraImage("extra image_2");
//		product.addExtraImage("extra-image 3");
//		
//		Product savedProduct = repository.save(product);
//		
//		assertThat(savedProduct.getImages().size()).isEqualTo(3);
//	}
	
//	@Test
//	public void testCreatedProductDetails() {
//		Product product = repository.findById(25).get();
//		product.addDetails("Camera", "15MP");
//		product.addDetails("OS", "Android");
//		
//		Product savedProduct = repository.save(product);
//		
//		assertThat(savedProduct.getDetails()).isNotEmpty();
//	}
	
//	@Test
//	public void testSet_List() {
//		//Set<Integer> integers = new HashSet<>();
//		List<Integer> integers = new ArrayList<>();
//		integers.add(1);
//		integers.add(2);
//		integers.add(3);
//		integers.add(1);
//		
//		for(Integer integer : integers) {
//			System.out.println(integer);
//		}
//		System.out.println(integers.size());
//	}
	
	@Test
	public void testIsDirectory() {
		String dir = "D:\\ShopmeEcommerce\\ShopmeProject\\ShopmeWebParent\\product-images\\36";
		  
		Path dirPath = Paths.get(dir);
		
		try {
			Files.list(dirPath).forEach(file ->{ 
				// Tests whether the file denoted by this pathname is a directory.
				if(Files.isDirectory(file)) {
						try {
							System.out.println( file + " is Directory");
							Files.delete(file);
						}catch (Exception e) {
							System.out.println("Not Directory");
						}
					}
				});
		} catch (IOException e) {
			System.out.println("Could not delete Directory " + dirPath);
		}
	}
	
}
