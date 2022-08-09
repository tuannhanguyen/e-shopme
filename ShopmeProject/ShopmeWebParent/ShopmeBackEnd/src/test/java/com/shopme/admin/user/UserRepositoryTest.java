package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest { 
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;

//	@Test
//	public void testCreateUseWithOneRole() {
//		
//		Role role = entityManager.find(Role.class, 1 );
//		
//		User userNhanNVT = new User("tuannhan6@gmail.com", "admin", "Nhan", "Nguyen");
//		
//		userNhanNVT.addRole(role);
//		
//		User usersaved = repo.save(userNhanNVT);
//		assertThat(usersaved.getId()).isGreaterThan(0);
//		
//	}
//	
//	@Test
//	public void testCreateUseWithTwoRole() {
//		
//		User userLinhTr = new User("tranlinhIt@gmail.com", "admin", "Linh", "Tran");
//		
//		Role roleEditoRole = new Role(3);
//		Role roleAssistantRole = new Role(5);
//		
//		userLinhTr.addRole(roleEditoRole);
//		userLinhTr.addRole(roleAssistantRole);
//		
//		User usersaved = repo.save(userLinhTr);
//		assertThat(usersaved.getId()).isGreaterThan(0);
//		
//	}
	
//	@Test
//	public void testListAllUsers() {
//		Iterable<User> listUsers = repo.findAll();
//		listUsers.forEach(user -> System.out.print(user));
//	}
//	
//	@Test
//	public void testUpdateUserDetails() {
//		User userNhan = repo.findById(11).get();
//		userNhan.setEnabled(true);
//		userNhan.setEmail("tuannhan6@gmail.com");
//		
//		repo.save(userNhan);
//	}
//	
//	@Test
//	public void testUpdateUserRole() {
//		User user = repo.findById(12).get();
//		Role roleEditor = new Role(3);
//		Role roleSalesPerson = new Role(2);
//
//		user.getRoles().remove(roleEditor);
//		user.addRole(roleSalesPerson);
//		
//		repo.save(user);
//	}
//	
//	@Test
//	public void testDeleteUser() {
//		Integer id = 12;
//		repo.deleteById(id);
//	}
//	
//	@Test
//	public void testGetUserByEmail() {
//		String email = "tuannhan@gmail.com";
//		User user = repo.getUserByEmail(email);
//		
//		assertThat(user).isNotNull();
//	}
	
//	@Test
//	public void testCountById() {
//		Integer id = 11;
//		Long countById = repo.countById(id);
//		
//		assertThat(countById).isNotNull().isGreaterThan(0);
//	}
	
//	@Test
//	public void updateDisableUser() {
//		Integer id = 13;
//		repo.updateEnabledStatus(id, false);
//	}
	
//	@Test
//	public void updateEnableUser() {
//		Integer id = 31;
//		repo.updateEnabledStatus(id, true);
//	}
//	
//	@Test
//	public void testListFirstPage() {
//		int pageNumber = 1;
//		int pageSize = 4;
//		
//		Pageable pageable = PageRequest.of(pageNumber, pageSize);
//		Page<User> page = repo.findAll(pageable);
//		
//		List<User> users = page.getContent();
//		
//		users.forEach(user -> System.out.println(user));
//		
//		assertThat(users.size()).isEqualTo(pageSize);
//	}
	
	@Test
	public void testSearchUsers() {
		String keywordString = "bruce";
		
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keywordString, pageable);
		
		List<User> users = page.getContent();
		
		users.forEach(user -> System.out.println(user));
		
		assertThat(users.size()).isGreaterThan(0);
	}
	
	
	
}
