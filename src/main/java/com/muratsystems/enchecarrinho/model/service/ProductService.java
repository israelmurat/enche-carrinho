package com.muratsystems.enchecarrinho.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muratsystems.enchecarrinho.model.BusinessException;
import com.muratsystems.enchecarrinho.model.domain.Product;
import com.muratsystems.enchecarrinho.model.dto.ProductDTO;
import com.muratsystems.enchecarrinho.model.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public ProductDTO addProduct(ProductDTO productDTO) {
		Optional<Product> optProduct =  productRepository.findByDescription(productDTO.getDescription());
		if (optProduct.isPresent()) {
			throw new BusinessException("Já existe um produto cadastrado com esta descrição!");
		}
		return new ProductDTO(productRepository.save(new Product(productDTO)));
	}
	
}
