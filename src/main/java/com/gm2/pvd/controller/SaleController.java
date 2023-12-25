package com.gm2.pvd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gm2.pvd.dto.ResponseDTO;
import com.gm2.pvd.dto.SaleDTO;
import com.gm2.pvd.service.SaleService;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/sale")

public class SaleController {


	
	private SaleService saleService;
	public SaleController(@Autowired SaleService saleService) {
		this.saleService = saleService;
	}
	
	@GetMapping()
	public ResponseEntity<?> getAll(){
		try {
			return new ResponseEntity<>(saleService.findAll(), HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping()
	public ResponseEntity<?> post(@Valid @RequestBody SaleDTO saleDTO){
		try {
			saleService.save(saleDTO);
			return new ResponseEntity<>(new ResponseDTO<>("Venda criada!"), HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		try {
			return new ResponseEntity<>(saleService.getById(id), HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
