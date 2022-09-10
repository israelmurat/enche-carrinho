package com.muratsystems.enchecarrinho.model.dto;

import java.math.BigDecimal;

import com.muratsystems.enchecarrinho.model.domain.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class ProductCartDTO {

	private String description;
	private BigDecimal unitaryValue;
	
	
	public ProductCartDTO(Product product) {
		this.description = product.getDescription();
		this.unitaryValue = product.getUnitaryValue();
	}
	
}
