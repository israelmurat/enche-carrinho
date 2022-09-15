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
	private BigDecimal percentualDiscountByType = BigDecimal.ZERO;
	private BigDecimal percentualProgressiveDiscount;
	private BigDecimal percentualDiscountByCoupon;
	private BigDecimal subTotalValue;
	private BigDecimal subTotalValueWithDiscount;

	public ProductCart(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public BigDecimal defineSubTotalValue() {
		if (product != null) {
			return product.getUnitaryValue().multiply(new BigDecimal(quantity.toString())).setScale(2,
					RoundingMode.HALF_EVEN);
		}
		return BigDecimal.ZERO;
	}

//	public BigDecimal defineTotalValueWithDiscount() {
//		if (!discountByType.equals(BigDecimal.ZERO)) {
//			if (product != null) {
//				BigDecimal indexTypeDiscount = BigDecimal.ONE
//						.subtract(discountByType.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
//				BigDecimal total = product.getUnitaryValue().multiply(new BigDecimal(quantity.toString()));
//				return total.multiply(indexTypeDiscount).setScale(2, RoundingMode.HALF_EVEN);
//			}
//		}
//		return defineTotalValue();
//	}
	
	public BigDecimal defineSubTotalValueWithDiscount() {
		if (product != null) {
			BigDecimal indexTypeDiscount = BigDecimal.ONE
					.subtract(percentualDiscountByType.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
			BigDecimal indexCouponDiscount = BigDecimal.ONE
					.subtract(percentualDiscountByCoupon.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
			BigDecimal indexProgressiveDiscount = BigDecimal.ONE
					.subtract(percentualProgressiveDiscount.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
			return defineSubTotalValue().multiply(indexTypeDiscount)
					.multiply(indexCouponDiscount).multiply(indexProgressiveDiscount)
					.setScale(2, RoundingMode.HALF_EVEN);
		}
		return BigDecimal.ZERO;
	}
	

}
