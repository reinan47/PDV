package com.gm2.pvd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gm2.pvd.Exception.InvalidOperationException;
import com.gm2.pvd.Exception.NoItemException;
import com.gm2.pvd.Exception.PasswordNotFoundException;
import com.gm2.pvd.dto.ResponseDTO;

@RestControllerAdvice
public class AplicationAdviceController {
	@ExceptionHandler(NoItemException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)

	public ResponseDTO<?> HandleNoItemException(NoItemException ex) {
		String messageError = ex.getMessage();
		return new ResponseDTO<>(messageError);
	}

	@ExceptionHandler(InvalidOperationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)

	public ResponseDTO<?> HandleInvalidOperationException(InvalidOperationException ex) {
		String messageError = ex.getMessage();
		return new ResponseDTO<>(messageError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)

	public ResponseDTO<?> HandleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<String> erros = new ArrayList<String>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessager = error.getDefaultMessage();
			erros.add(errorMessager);
		});
		return new ResponseDTO<>(erros);
	}


	 @ExceptionHandler(PasswordNotFoundException.class)
	 
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	  
	 public ResponseDTO<?>
	 HandlePasswordNotFoundException(PasswordNotFoundException ex) { 
		 String messageError = ex.getMessage(); return new ResponseDTO<>(messageError);
	 }
	 
	 @ExceptionHandler(UsernameNotFoundException.class)
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	  
	 public ResponseDTO<?>
	 HandleUsernameNotFoundException(UsernameNotFoundException ex) { 
		 String messageError = ex.getMessage(); return new ResponseDTO<>(messageError);
	 }
	 
}
