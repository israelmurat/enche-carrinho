package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductCartDTO {

	private ProductDTO product;
	private Integer quantity;
	private BigDecimal discountByType = BigDecimal.ZERO;
	
}
