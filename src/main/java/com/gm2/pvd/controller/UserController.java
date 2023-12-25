package com.gm2.pvd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gm2.pvd.dto.ResponseDTO;
import com.gm2.pvd.dto.UserDTO;
import com.gm2.pvd.service.UserService;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/user")

public class UserController {

	private UserService userService;

	public UserController(@Autowired UserService userService) {
		this.userService = userService;
	}

	@GetMapping()
	public ResponseEntity<?> getAll(){
		try {
			return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		try {
			return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping()
	public ResponseEntity<?> post(@Valid @RequestBody UserDTO user) {
		try {
			user.setEnabled(true);
			return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping()
	public ResponseEntity<?> put(@Valid @RequestBody UserDTO user){
		try {
			return new ResponseEntity<>(userService.update(user) , HttpStatus.OK);
		} 
		catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		try {
			userService.deleteById(id);
			return new ResponseEntity<>("Usuário deletado", HttpStatus.OK);
		}catch (EmptyResultDataAccessException error) {
			return new  ResponseEntity<>(new ResponseDTO<>("Usuário naõ encontrado"), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
