package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
@Transactional
public class UserService {
	
	public static final int USERS_PER_PAGE = 4;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User getByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}
	
	public List<User> listAll(){
		return (List<User>) userRepository.findAll();
	}
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper){
		
		helper.listEntites(pageNum, USERS_PER_PAGE, userRepository);
	}
	
	public List<Role> listRoles(){
		return (List<Role>) roleRepository.findAll()	;
	}
	
	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		
		
		if(isUpdatingUser) {
			User existingUser = userRepository.findById(user.getId()).get();
			
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}
			
		} else {
			encodePassword(user);
		}
		
		return userRepository.save(user);
	}
	
	public User updateAccount(User userInForm) {
		User userInDbUser = userRepository.findById(userInForm.getId()).get();
		
		if(!userInForm.getPassword().isEmpty()) {
			userInDbUser.setPassword(userInForm.getPassword());
			encodePassword(userInDbUser);
		}
		
		if(userInForm.getPhotos() != null) {
			userInDbUser.setPhotos(userInForm.getPhotos());
		}
		
		userInDbUser.setFirstName(userInForm.getFirstName());
		userInDbUser.setLastName(userInForm.getLastName());
		
		return userRepository.save(userInDbUser);
	}
	
	public void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepository.getUserByEmail(email);
		
		if(userByEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		
		if(isCreatingNew) {
			if(userByEmail != null) return false;
		} else {
			if(userByEmail.getId() != id) {
				return false;
			}
		}
		
		return true;
	}
	
	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
			
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		boolean countById = userRepository.existsById(id);
		if(countById == false) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		
		userRepository.deleteById(id);
	}
	
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepository.updateEnabledStatus(id, enabled);
	}
	

}  
