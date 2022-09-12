package com.muratsystems.enchecarrinho.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muratsystems.enchecarrinho.api.dto.ProductDTO;
import com.muratsystems.enchecarrinho.domain.exception.BusinessException;
import com.muratsystems.enchecarrinho.domain.model.Product;
import com.muratsystems.enchecarrinho.domain.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public ProductDTO addProduct(ProductDTO productDTO) {
		Optional<Product> optProduct = productRepository.findByDescription(productDTO.getDescription());
		if (optProduct.isPresent()) {
			throw new BusinessException("Já existe um produto cadastrado com esta descrição!");
		}
		return new ProductDTO(productRepository.save(new Product(productDTO)));
	}
	
	public ProductDTO findById(Long idProduct) {
		Optional<Product> optProduct = productRepository.findById(idProduct);
		if (optProduct.isPresent()) {
			return new ProductDTO(optProduct.get());
		}
		throw new BusinessException("Produto não encontrado!");
	}
	
}
