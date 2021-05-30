package com.apirest.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.app.entity.User;
import com.apirest.app.service.userService;


@RestController
@RequestMapping("/api/users")
public class userController {
	
	@Autowired
	private userService UserService;
	
	//Create new user
	@PostMapping
	public ResponseEntity<?> create (@RequestBody User user){
		return ResponseEntity.status(HttpStatus.CREATED).body(UserService.save(user));
		
 }
	
	//Read user
	@GetMapping("/{id}")
	public ResponseEntity<?> read (@PathVariable(value = "id") Long userId){
		Optional<User> oUser = UserService.findById(userId);
		
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oUser);
	}
	//Update User
	@PutMapping("/{id}")
	public ResponseEntity<?> update (@RequestBody User userDetails, @PathVariable(value = "id") Long userId){
		Optional<User>	User = UserService.findById(userId);
		
		if(!User.isPresent()){
			return ResponseEntity.notFound().build();
			
		}
		
		User.get().setName(userDetails.getName());
		User.get().setSurname(userDetails.getSurname());
		User.get().setEmail(userDetails.getEmail());
		User.get().setEnabled(userDetails.getEnabled());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(UserService.save(User.get()));
		
	}
	
	//Delete User
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long userId){
		if(!UserService.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
			
		}
		
		UserService.deleteById(userId);
		return ResponseEntity.ok().build();
		
	}
	
	//Read all Users
	@GetMapping
	public List<User> readAll(){
		List<User> users = StreamSupport
				.stream(UserService.findAll().spliterator(), false)
				.collect(Collectors.toList());
				
		return users;
	}
}












