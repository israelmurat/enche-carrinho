package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShoppingCartDTO {

	private List<ProductCartDTO> productsCart = new ArrayList<>();
	private CouponDTO coupon;
	private BigDecimal progressiveDiscount;
	private BigDecimal totalValue;
	private BigDecimal totalValueWithDiscount;

	public void addProducts(ProductDTO product) {
		boolean isAddProduct = false;
		BigDecimal discountByCoupon = coupon != null ? coupon.getDiscountPercentage() : BigDecimal.ZERO;
		for (ProductCartDTO productCart : productsCart) {
			if (!isAddProduct && productCart.getProduct().equals(product)) {
				productCart.setQuantity(productCart.getQuantity() + 1);
				productCart.setDiscountByCoupon(discountByCoupon);
				isAddProduct = true;
			}
		}
		if (!isAddProduct) {
			ProductCartDTO newProductCart = new ProductCartDTO(product, 1, discountByCoupon);
			productsCart.add(newProductCart);
		}
		setDiscountByType();
		setDiscountByTotal();
		setTotals();
	}

	private void setDiscountByType() {
		Map<String, Integer> mapProduct = new HashMap<>();
		for (ProductCartDTO productCart : productsCart) {
			String key = productCart.getProduct().getType();
			if (mapProduct.containsKey(key)) {
				mapProduct.put(key, mapProduct.get(key) + productCart.getQuantity());
			} else {
				mapProduct.put(key, productCart.getQuantity());
			}
		}
		for (ProductCartDTO productCart : productsCart) {
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
		BigDecimal totalValueCart = productsCart.stream().map(p -> p.defineTotalValueWithDiscount())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (totalValueCart.compareTo(new BigDecimal("1000")) > 0) {
			progressiveDiscount = new BigDecimal("5");
		} else if (totalValueCart.compareTo(new BigDecimal("5000")) > 0) {
			progressiveDiscount = new BigDecimal("7");
		} else if (totalValueCart.compareTo(new BigDecimal("10000")) > 0) {
			progressiveDiscount = new BigDecimal("10");
		}
	}

	private void setTotals() {
		BigDecimal indexProgressiveDiscount = BigDecimal.ONE
				.subtract(progressiveDiscount.divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN));
		totalValue = productsCart.stream()
				.map(p -> p.defineTotalValue())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		totalValueWithDiscount = (productsCart.stream()
				.map(p -> p.defineTotalValueWithDiscount())
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				).multiply(indexProgressiveDiscount).setScale(2, RoundingMode.HALF_EVEN);
	}

}
