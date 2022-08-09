package com.shopme.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.shopme.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository repo;
	
//	@Test
//	public void testListEnabledRepository() {
//		List<Category> categories = repo.findAllEnabled();
//		categories.forEach(cat ->{
//			System.out.println(cat.getName() + "--" + cat.isEnabled());
//		});
//	}
	
	@Test
	public void testFindCategoryByAlias() {
		String alias = "xx";
		Category category = repo.findByAliasEnabled(alias);
		
		assertThat(category).isNotNull();
	}
}
