package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.desktop.SystemSleepEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.w3c.dom.ls.LSException;

import com.shopme.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository repo;

//	@Test
//	public void testCreateRootCategory() {
//		
//		Category category = new Category("Electronics");
//		Category savedCategory = repo.save(category);
//		
//		assertThat(savedCategory.getId()).isGreaterThan(0);
//	}

//	@Test
//	public void  testCreateSubCategory() {
//		Category parent = new Category(8);
//		Category subCate = new Category("iPhone", parent);
//		Category savedSubCategory = repo.save(subCate);
//		
//		assertThat(savedSubCategory.getId()).isGreaterThan(0);
//	}

//	@Test
//	public void testGetCategory() {
//		Category category = repo.findById(2).get();
//		System.out.println(category.getName());
//		
//		Set<Category> children = category.getChildren();
//		for(Category cate : children) {
//			System.out.println(cate.getName());
//		}
//		
//		assertThat(children.size()).isGreaterThan(0);
//		
//	}

//	@Test
//	public void testPrintHierarchicalCategories() {
//		List<Category> categories = (List<Category>) repo.findAll();
//
//		for (Category category : categories) {
//			if (category.getParent() == null) {
//				System.out.println(category.getName());
//
//				Set<Category> children = category.getChildren();
//
//				for (Category subCategory : children) {
//					System.out.println("--" + subCategory.getName());
//
//					Set<Category> childrenSub = subCategory.getChildren();
//					for (Category sub : childrenSub) {
//						System.out.println("---" + sub.getName());
//					}
//				}
//			}
//		}
//	}
	
//	@Test
//	public void listRootCategories(){
//		List<Category> list = repo.findRootCategories();
//		list.forEach(cat -> System.out.println(cat.getName()));
//	}
	
//	@Test
//	public void testFindByName() {
//		String name = "Computer1";
//		Category category = repo.findByName(name);
//		
//		assertThat(category).isNotNull();
//		assertThat(category.getName()).isEqualTo(name);
//	}
	
//	@Test
//	public void testFindAlias() {
//		String alias = "Hp-Envy";
//		Category category = repo.findByAlias(alias);
//		
//		assertThat(category).isNotNull();
//		assertThat(category.getAlias()).isEqualTo(alias);
//		
//	}
	
	@Test
	public void testFindAlias() {
		SortedSet<String> setNumbers = new TreeSet<>(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o2.compareTo(o1);
			}
		});
		String name1 = "cb";
		String name2 = "zq";
		String name3 = "aq";
		setNumbers.addAll(Arrays.asList(name1, name2, name3));
		System.out.println("Sorted Set: " + setNumbers);
		
	}
	
}
