package com.muratsystems.enchecarrinho.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ShoppingCart {

	private List<ProductCart> productsCart = new ArrayList<>();
	private Coupon coupon;
	private BigDecimal progressiveDiscount;
	private BigDecimal totalValue;
	private BigDecimal totalValueWithDiscount;

	public void addProducts(Product product) {
		boolean isAddProduct = false;
		for (ProductCart productCart : productsCart) {
			if (!isAddProduct && productCart.getProduct().equals(product)) {
				productCart.setQuantity(productCart.getQuantity() + 1);
				isAddProduct = true;
				break;
			}
		}
		if (!isAddProduct) {
			ProductCart newProductCart = new ProductCart(product, 1);
			productsCart.add(newProductCart);
		}
		setDiscountByType();
		setDiscountByTotal();
		defineTotals();
	}

	private void setDiscountByType() {
		Map<String, Integer> mapProduct = new HashMap<>();
		for (ProductCart productCart : productsCart) {
			String key = productCart.getProduct().getType();
			if (mapProduct.containsKey(key)) {
				mapProduct.put(key, mapProduct.get(key) + productCart.getQuantity());
			} else {
				mapProduct.put(key, productCart.getQuantity());
			}
		}
		for (ProductCart productCart : productsCart) {
			productCart.setDiscountByType(BigDecimal.ZERO);
			String key = productCart.getProduct().getType();
			if (mapProduct.containsKey(key)) {
				if (mapProduct.get(key).compareTo(Integer.valueOf(10)) >= 0) {
					productCart.setDiscountByType(BigDecimal.TEN);
				}
			}
		}
	}

	private void setDiscountByTotal() {
		progressiveDiscount = BigDecimal.ZERO;
		BigDecimal totalValueCart = productsCart.stream().map(p -> p.defineTotalValue())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (totalValueCart.compareTo(new BigDecimal("1000")) > 0 
				&& totalValueCart.compareTo(new BigDecimal("5000")) <= 0) {
			progressiveDiscount = new BigDecimal("5");
		} else if (totalValueCart.compareTo(new BigDecimal("5000")) > 0 
				&& totalValueCart.compareTo(new BigDecimal("10000")) <= 0) {
			progressiveDiscount = new BigDecimal("7");
		} else if (totalValueCart.compareTo(new BigDecimal("10000")) > 0) {
			progressiveDiscount = new BigDecimal("10");
		}
	}

	public void defineTotals() {
		BigDecimal percCouponDiscount = coupon != null ? coupon.getDiscountPercentage() : BigDecimal.ZERO;
		BigDecimal indexCouponDiscount = BigDecimal.ONE
				.subtract(percCouponDiscount.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
		BigDecimal indexProgressiveDiscount = BigDecimal.ONE
				.subtract(progressiveDiscount.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
		totalValue = productsCart.stream()
				.map(p -> p.defineTotalValue())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		totalValueWithDiscount = (productsCart.stream()
				.map(p -> p.defineTotalValueWithDiscount())
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				).multiply(indexCouponDiscount)
				.multiply(indexProgressiveDiscount).setScale(2, RoundingMode.HALF_EVEN);
	}

}
