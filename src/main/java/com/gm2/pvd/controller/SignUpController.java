package com.gm2.pvd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gm2.pvd.dto.ResponseDTO;
import com.gm2.pvd.dto.UserDTO;
import com.gm2.pvd.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sign-up")
public class SignUpController {
	private UserService userService;

	public SignUpController(@Autowired UserService userService) {
		this.userService = userService;
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


}
