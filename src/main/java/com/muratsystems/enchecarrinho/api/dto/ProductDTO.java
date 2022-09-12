package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;

import com.muratsystems.enchecarrinho.domain.model.Product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter 
@NoArgsConstructor @ToString @EqualsAndHashCode
public class ProductDTO {

	private Long id;
	private String description;
	private String type;
	private BigDecimal unitaryValue;
	
	public ProductDTO(Product product) {
		id = product.getId();
		description = product.getDescription();
		type = product.getType();
		unitaryValue = product.getUnitaryValue();
	}
	
}
