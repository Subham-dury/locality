package com.locality.backend.controller;

import java.util.Map;

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

import com.locality.backend.entity.User;
import com.locality.backend.exception.DataExistsException;
import com.locality.backend.exception.DataNotFoundException;
import com.locality.backend.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<User> saveUser(@RequestBody User user) throws DataExistsException{
		
		return new ResponseEntity<User>(this.userService.saveUser(user), HttpStatus.CREATED);
			
	}
	
	
	@GetMapping("/")
	public ResponseEntity<User> findUser(@RequestBody User user) throws DataNotFoundException{
	
		return ResponseEntity.ok(this.userService.getUser(user));
		
	}

	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@RequestBody User user,
			@PathVariable String userId) throws DataNotFoundException{
		
		return ResponseEntity.ok(this.userService.updateUser(user, Long.parseLong(userId)));
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable String userId) throws DataNotFoundException{
		
		Boolean deleteUser = this.userService.deleteUser(Long.parseLong(userId));
		if(deleteUser) {
			return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
		}
		return (ResponseEntity<?>) ResponseEntity.badRequest();
	}
}