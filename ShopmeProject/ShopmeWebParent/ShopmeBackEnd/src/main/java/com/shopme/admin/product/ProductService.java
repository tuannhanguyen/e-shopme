package com.shopme.admin.product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Product;
import com.shopme.common.exception.ProductNotFoundException;


@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	public static final int PRODUCTS_PER_PAGE = 5;  
	
	public List<Product> listAll(){
		return (List<Product>) repo.findAll();
	}
	
	public Page<Product> listByPage(int pageNum, String sortField, 
			String sortDir, String keyword, Integer categoryId){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum-1, PRODUCTS_PER_PAGE, sort);
		
		if(keyword != null && !keyword.isEmpty()) {
			if(categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				return repo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
			}
			return repo.findAll(keyword, pageable);
		}
		// keyword is null
		if(categoryId != null && categoryId > 0) {
			String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
			return repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
		}
		return repo.findAll(pageable);
	}
	
	public Product save(Product product) {
		if(product.getId() == null) {
			product.setCreatedDate(new Date());
		}
		
		if(product.getAlias() == null || product.getAlias().isEmpty()) {
			product.setAlias(product.getName().replace(" ", "-"));
		} else {
			product.setAlias(product.getAlias().replace(" ", "-"));
		}
		
		product.setUpdatedDate(new Date());
		
		return repo.save(product);
	}
	
	public Product saveProductPrice(Product productInForm) {
		Product productInDb = repo.findById(productInForm.getId()).get();
		productInDb.setPrice(productInForm.getPrice());
		productInDb.setCost(productInForm.getCost());
		productInDb.setDiscountPercent(productInForm.getDiscountPercent());
		
		return repo.save(productInDb);
	}
	
	public String checkUnique(Integer id, String name, String alias) {
		boolean isCreatingNew = (id == null);
		
		Product findByName = repo.findByName(name);
		Product findByAlias = repo.findByAlias(alias);
		
		if (isCreatingNew) {
			if (findByName != null) {
				return "DuplicateName";
			} else {
				if (findByAlias != null) {
					return "DuplicateAlias";
				}
			}
		} else {
			if (findByName != null && findByName.getId() != id) {
				return "DuplicateName";
			} 
			if (findByAlias != null && findByAlias.getId() != id) {
				return "DuplicateAlias";
			}
		}
		
		return "OK";
	}
	
	public void updateEnabledStatus(Integer id, boolean enabled ) throws ProductNotFoundException {
		try {
			Product product = repo.findById(id).get();
			repo.updateEnabledStatus(id, enabled);
		} catch (NoSuchElementException e) {
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}
	}
	
	public void delete(Integer id) throws ProductNotFoundException {
		try {
			Product product = repo.findById(id).get();
			repo.deleteById(id);
		} catch (NoSuchElementException e) {
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}
	}
	
	public Product get(Integer id) throws ProductNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}
	}
	
	
}
