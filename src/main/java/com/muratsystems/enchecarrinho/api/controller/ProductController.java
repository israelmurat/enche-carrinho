package com.muratsystems.enchecarrinho.api.controller;

import java.util.List;

import javax.validation.Valid;

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
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{idProduct}")
	public ResponseEntity<ProductDTO> getById(@PathVariable Long idProduct) {
		return new ResponseEntity<>(productService.findById(idProduct), HttpStatus.OK);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) 
	public ProductDTO addProduct(@RequestBody @Valid ProductDTO product) {
		return productService.addProduct(product);
	}
	
	@PutMapping(value = "/{idProduct}")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @PathVariable Long idProduct, 
			@RequestBody ProductDTO productDTO) {
		return new ResponseEntity<>(productService.update(idProduct, productDTO), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{idProduct}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long idProduct) {
		productService.delete(idProduct);
		return ResponseEntity.noContent().build();
	}
	
}
