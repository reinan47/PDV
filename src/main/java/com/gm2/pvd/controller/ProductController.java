package com.gm2.pvd.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.gm2.pvd.dto.ProductDTO;
import com.gm2.pvd.dto.ResponseDTO;
import com.gm2.pvd.entity.Product;
import com.gm2.pvd.repository.ProductRepository;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/product")
public class ProductController {

	private ModelMapper mapper = new ModelMapper();

	private ProductRepository productRepository;

	public ProductController(@Autowired ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@GetMapping()
	public ResponseEntity<?> getAll() {
		try {
			return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping()
	public ResponseEntity<?> post(@Valid @RequestBody ProductDTO product){
		try {
			return new ResponseEntity<>(productRepository.save(mapper.map(product, Product.class)), HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping()
	public ResponseEntity<?> put(@Valid @RequestBody ProductDTO product) {
		try {
			return new ResponseEntity<>(productRepository.save(mapper.map(product, Product.class)), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>(new ResponseDTO<>("Produto deletado"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
