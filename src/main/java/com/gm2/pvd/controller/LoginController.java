package com.gm2.pvd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gm2.pvd.dto.LoginDTO;
import com.gm2.pvd.dto.ResponseDTO;
import com.gm2.pvd.dto.TokenDTO;
import com.gm2.pvd.security.CustomUserDetailService;
import com.gm2.pvd.security.JwtService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Autowired
	private JwtService jwtService;
	
	@Value("${security.jwt.expiration}")
	private String expiation;
	
	@PostMapping
	public ResponseEntity<?> post(@Valid @RequestBody LoginDTO loginData){
		try {
			String token = jwtService.generateToken(loginData.getUserName());
			userDetailService.verifyUserCredentials(loginData);
			return new ResponseEntity<>(new TokenDTO(token, expiation), HttpStatus.OK);
			//gerar o token
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.UNAUTHORIZED);
		}
	}
}
