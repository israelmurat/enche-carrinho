package com.muratsystems.enchecarrinho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muratsystems.enchecarrinho.model.dto.ProductDTO;
import com.muratsystems.enchecarrinho.model.service.ProductService;

@RestController
@RequestMapping(value = "/produtos")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<ProductDTO> getAllProducts() {
		return null;
	}
	
	@PostMapping(value = "/new")
	public void saveProduct() {
		
	}
	
	@PutMapping(value = "/{id}/update")
	public void updateProduct() {
		
	}
	
	@DeleteMapping(value = "/{id}/delete")
	public void deleteProduct() {
		
	}
	
}
