package com.shopme.admin.category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
	
	@Query("select c from Category c where c.parent is null")
	public List<Category> findRootCategories(Sort sort);
	
	@Query("select c from Category c where c.parent is null")
	public Page<Category> findRootCategories(Pageable pageable);
	
	@Query("select c from Category c where c.name like %?1%")
	public Page<Category> search(String keyword, Pageable pageable);
	
	@Query("update from Category c set c.enabled = ?1 where c.id = ?2")
	@Modifying
	public void editEnabledStatus(boolean enabled, Integer id);
	
	public Category findByName(String name);
	
	public Category findByAlias(String alias);
	
}
