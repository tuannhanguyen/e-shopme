package com.shopme.admin.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.Product;


public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
	
	@Query("select p from Product p where p.name = ?1")
	public Product findByName(String name);
	
	@Query("select p from Product p where p.alias = ?1")
	public Product findByAlias(String alias);
	
	@Query("update from Product p set p.enabled = ?2 where p.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	@Query("select p from Product p where p.name like %?1% "
			+ "or p.shortDescription like %?1%"
			+ "or p.fullDescription like %?1%"
			+ "or p.brand.name like %?1%"
			+ "or p.category.name like %?1%")
	public Page<Product> findAll(String keyword, Pageable pageable);
	
	@Query("select p from Product p where p.category.id = ?1 "
			+ "or p.category.allParentIDs like %?2%")
	public Page<Product> findAllInCategory(Integer categoryId, String categoryIdMatch, Pageable pageable);
	
	@Query("select p from Product p where (p.category.id = ?1 "
			+ "or p.category.allParentIDs like %?2%) "
			+ "AND (p.name like %?3% "
			+ "or p.shortDescription like %?3% "
			+ "or p.fullDescription like %?3% "
			+ "or p.brand.name like %?3% "
			+ "or p.category.name like %?3%)")
	public Page<Product> searchInCategory(Integer categoryId, String categoryIdMatch, String keyword, Pageable pageable);

}
