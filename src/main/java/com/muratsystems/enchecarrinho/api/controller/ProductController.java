package com.muratsystems.enchecarrinho.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.muratsystems.enchecarrinho.api.dto.ProductDTO;
import com.muratsystems.enchecarrinho.domain.service.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<ProductDTO> getAllProducts() {
		return null;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) 
	public ProductDTO addProduct(@RequestBody @Valid ProductDTO product) {
		return productService.addProduct(product);
	}
	
	@PutMapping(value = "/{id}/update")
	public void updateProduct() {
		
	}
	
	@DeleteMapping(value = "/{id}/delete")
	public void deleteProduct() {
		
	}
	
}
