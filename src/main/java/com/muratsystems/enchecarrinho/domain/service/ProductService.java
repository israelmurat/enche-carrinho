package com.muratsystems.enchecarrinho.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProductDTO addProduct(ProductDTO productDTO) {
		if (productRepository.findByDescription(productDTO.getDescription()).isPresent()) {
			throw new BusinessException("Já existe um produto cadastrado com esta descrição!");
		}
		return toDto(productRepository.save(toEntity(productDTO)));
	}
	
	public List<ProductDTO> getAll() {
		return toCollectionDto(productRepository.findAll());
	}
	
	public ProductDTO findById(Long idProduct) {
		return toDto(productRepository.findById(idProduct)
				.orElseThrow(() -> new BusinessException("Produto não encontrado!")));
	}
	
	public ProductDTO update(Long idProduct, ProductDTO productDTO) {
		productRepository.findById(idProduct)
				.orElseThrow(() -> new BusinessException("Produto não cadastrado!"));
		var optProductDescription = productRepository.findByDescription(productDTO.getDescription());
		if (optProductDescription.isPresent()) {
			if (!optProductDescription.get().getId().equals(idProduct)) {
				throw new BusinessException("Descrição já está sendo usada para outro produto!");
			}
		}
		var product = toEntity(productDTO);
		product.setId(idProduct);
		return toDto(productRepository.save(product));
	}
	
	public void delete(Long idProduct) {
		if (!productRepository.existsById(idProduct)) {
			throw new BusinessException("Produto não cadastrado!");
		}
		productRepository.deleteById(idProduct);
	}
	
	private Product toEntity(ProductDTO productDTO) {
		return modelMapper.map(productDTO, Product.class);
	}
	
	private ProductDTO toDto(Product product) {
		return modelMapper.map(product, ProductDTO.class);
	}
	
	private List<ProductDTO> toCollectionDto(List<Product> products) {
		return products.stream()
				.map(product -> toDto(product))
				.collect(Collectors.toList());
	}
	
}
