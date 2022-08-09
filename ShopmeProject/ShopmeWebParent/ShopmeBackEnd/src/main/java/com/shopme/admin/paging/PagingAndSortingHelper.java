package com.shopme.admin.paging;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.method.support.ModelAndViewContainer;


public class PagingAndSortingHelper {
	private ModelAndViewContainer model;
	private String moduleURL;
	private String listName;
	private String sortField;
	private String sortDir;
	private String keyword;
	

	public PagingAndSortingHelper(ModelAndViewContainer model, String moduleURL, String listName,
			String sortField, String sortDir, String keyword) {
		this.model = model;
		this.moduleURL = moduleURL;
		this.listName = listName;
		this.sortField = sortField;
		this.sortDir = sortDir;
		this.keyword = keyword;
	}
	
	public void updateModelAttributes(int pageNum, Page<?> page) {
		List<?> listItems = page.getContent();
		int pageSize = page.getSize();

		long startCount = (pageNum - 1) * pageSize + 1;
		long endCount = startCount + pageSize - 1;
		model.addAttribute("totalItems", page.getTotalElements());

		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute(listName, listItems);
		
		model.addAttribute("moduleURL", moduleURL);
	}
	
	public void listEntites(int pageNum, int pageSize, SearchRepository<?, Integer> repository) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		Page<?> page = null;
		
		if (keyword != null) {
			page = repository.findAll(keyword, pageable);
		} else {
			page = repository.findAll(pageable);
		}
		
		updateModelAttributes(pageNum, page);
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSortField() {
		return sortField;
	}

	public String getSortDir() {
		return sortDir;
	}
}
