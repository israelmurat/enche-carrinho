package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ShoppingCartDTO {
	
	private List<ProductCartDTO> productsCart = new ArrayList<>();
	private CouponDTO coupon;
	private BigDecimal progressiveDiscount;
	
	public void addProducts(ProductDTO product) {
		boolean isAddProduct = false;
		BigDecimal totalValueCart = BigDecimal.ZERO;
		for (ProductCartDTO productCart : productsCart) {
			if (!isAddProduct && productCart.getProduct().equals(product)) {
				productCart.setQuantity(productCart.getQuantity() + 1);
				isAddProduct = true;
			}
			totalValueCart = totalValueCart.add(productCart.getTotalValue());
		}
		if (!isAddProduct) {
			ProductCartDTO newProductCart = new ProductCartDTO(product, 1);
			productsCart.add(newProductCart);
			totalValueCart = totalValueCart.add(newProductCart.getTotalValue());
		}
	}
	
	private void setDiscountByTotal(BigDecimal totalValueCart) {
		progressiveDiscount = BigDecimal.ZERO;
		if (totalValueCart.compareTo(new BigDecimal("1000")) > 0) {
			progressiveDiscount = new BigDecimal("5");
		} else if (totalValueCart.compareTo(new BigDecimal("5000")) > 0) {
			progressiveDiscount = new BigDecimal("7");
		} else if (totalValueCart.compareTo(new BigDecimal("10000")) > 0) {
			progressiveDiscount = new BigDecimal("10");
		}
	}

}
