package com.muratsystems.enchecarrinho.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductCart {

	private Product product;
	private Integer quantity;
	private BigDecimal discountByType = BigDecimal.ZERO;

	public ProductCart(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public BigDecimal defineTotalValue() {
		if (product != null) {
			return product.getUnitaryValue().multiply(new BigDecimal(quantity.toString())).setScale(2,
					RoundingMode.HALF_EVEN);
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal defineTotalValueWithDiscount() {
		if (!discountByType.equals(BigDecimal.ZERO)) {
			if (product != null) {
				BigDecimal indexTypeDiscount = BigDecimal.ONE
						.subtract(discountByType.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
				BigDecimal total = product.getUnitaryValue().multiply(new BigDecimal(quantity.toString()));
				return total.multiply(indexTypeDiscount).setScale(2, RoundingMode.HALF_EVEN);
			}
		}
		return defineTotalValue();
	}

}
