package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;

import com.muratsystems.enchecarrinho.domain.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ProductDTO {

	private Long id;
	private String description;
	private BigDecimal unitaryValue;
	
	public ProductDTO(Product product) {
		id = product.getId();
		description = product.getDescription();
		unitaryValue = product.getUnitaryValue();
	}
	
}
