package com.muratsystems.enchecarrinho.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @ToString @EqualsAndHashCode
public class ProductCart {

	private Product product;
	private Integer quantity;
	private BigDecimal percentualDiscountByType = BigDecimal.ZERO;
	private BigDecimal percentualProgressiveDiscount = BigDecimal.ZERO;
	private BigDecimal percentualDiscountByCoupon = BigDecimal.ZERO;
	private BigDecimal subTotalValue = BigDecimal.ZERO;
	private BigDecimal subTotalValueWithDiscount = BigDecimal.ZERO;

	public ProductCart(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public void defineSubtotals() {
		defineSubTotalValue();
		defineSubTotalValueWithDiscount();
	}

	private void defineSubTotalValue() {
		subTotalValue = BigDecimal.ZERO;
		if (product != null) {
			subTotalValue = product.getUnitaryValue().multiply(new BigDecimal(quantity.toString())).setScale(2,
					RoundingMode.HALF_EVEN);
		}
	}
	
	private void defineSubTotalValueWithDiscount() {
		subTotalValueWithDiscount = subTotalValue;
		if (product != null) {
			BigDecimal indexTypeDiscount = BigDecimal.ONE
					.subtract(percentualDiscountByType.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
			BigDecimal indexCouponDiscount = BigDecimal.ONE
					.subtract(percentualDiscountByCoupon.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
			BigDecimal indexProgressiveDiscount = BigDecimal.ONE
					.subtract(percentualProgressiveDiscount.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
			subTotalValueWithDiscount = subTotalValueWithDiscount.multiply(indexTypeDiscount)
					.multiply(indexCouponDiscount).multiply(indexProgressiveDiscount)
					.setScale(2, RoundingMode.HALF_EVEN);
		}
	}

}
