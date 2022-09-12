package com.muratsystems.enchecarrinho.api.dto;

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
public class ProductCartDTO {

	private ProductDTO product;
	private Integer quantity;
	private BigDecimal discountByType = BigDecimal.ZERO;
	private BigDecimal discountByCoupon = BigDecimal.ZERO;

	public ProductCartDTO(ProductDTO product, Integer quantity, BigDecimal discountByCoupon) {
		this.product = product;
		this.quantity = quantity;
		this.discountByCoupon = discountByCoupon != null ? discountByCoupon : BigDecimal.ZERO;
	}

	public BigDecimal getTotalValue() {
		if (product != null) {
			return product.getUnitaryValue().multiply(new BigDecimal(quantity.toString())).setScale(2,
					RoundingMode.HALF_EVEN);
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal getTotalValueWithDiscount() {
		if (!discountByCoupon.equals(BigDecimal.ZERO) || !discountByType.equals(BigDecimal.ZERO)) {
			if (product != null) {
				BigDecimal percent = new BigDecimal("100");
				BigDecimal total = product.getUnitaryValue().multiply(new BigDecimal(quantity.toString()));
				BigDecimal valueDiscountCoupon = product.getUnitaryValue().multiply(discountByCoupon).divide(percent, 2,
						RoundingMode.HALF_EVEN);
				BigDecimal valueDiscountType = product.getUnitaryValue().multiply(discountByType).divide(percent, 2,
						RoundingMode.HALF_EVEN);
				return total.subtract(valueDiscountCoupon).subtract(valueDiscountType).setScale(2,
						RoundingMode.HALF_EVEN);
			}
		}
		return getTotalValue();
	}

}
