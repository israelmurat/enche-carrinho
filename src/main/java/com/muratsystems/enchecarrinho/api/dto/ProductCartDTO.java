package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;

import com.muratsystems.enchecarrinho.domain.model.Product;

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
