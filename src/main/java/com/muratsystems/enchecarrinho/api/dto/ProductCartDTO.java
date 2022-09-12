package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @NoArgsConstructor
public class ProductCartDTO {

	private ProductDTO product;
	private Integer quantity;
	private BigDecimal discountByType;
	
	public ProductCartDTO(ProductDTO product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public BigDecimal getTotalValue() {
		if (product != null) {
			return product.getUnitaryValue().multiply(new BigDecimal(quantity.toString()));
		}
		return BigDecimal.ZERO;
	}
	
}
