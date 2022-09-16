package com.muratsystems.enchecarrinho.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.muratsystems.enchecarrinho.domain.exception.BusinessException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @ToString @EqualsAndHashCode
public class ShoppingCart {

	private List<ProductCart> productsCart = new ArrayList<>();
	private Coupon coupon;
	private BigDecimal totalValue = BigDecimal.ZERO;
	private BigDecimal totalValueWithDiscount = BigDecimal.ZERO;

	public void addProduct(Product product) {
		addToCart(product);
		mapAndDefineDiscountByType();
		defineDiscountsProgressiveAndCoupon();
		defineTotals();
	}
	
	private void addToCart(Product product) {
		boolean isAddProduct = false;
		for (var productCart : productsCart) {
			if (!isAddProduct && productCart.getProduct().equals(product)) {
				productCart.setQuantity(productCart.getQuantity() + 1);
				isAddProduct = true;
				break;
			}
		}
		if (!isAddProduct) {
			var newProductCart = new ProductCart(product, 1);
			productsCart.add(newProductCart);
		}
	}
	
	public void removeProduct(Long idProducut) {
		if (!isRemovedProduct(idProducut)) {
			throw new BusinessException("Produto informado não está no carrinho!");
		}
		mapAndDefineDiscountByType();
		defineDiscountsProgressiveAndCoupon();
		defineTotals();
	}
	
	private boolean isRemovedProduct(Long idProducut) {
		for (Iterator<ProductCart> itProductCart = productsCart.iterator(); itProductCart.hasNext(); ) {
			var productCart = itProductCart.next();
			if (productCart.getProduct().getId().equals(idProducut)) {
				if (productCart.getQuantity().compareTo(Integer.valueOf(1)) > 0) {
					productCart.setQuantity(productCart.getQuantity() - 1);
				} else {
					itProductCart.remove();
				}
				return true;
			}
		}
		return false;
	}

	private void mapAndDefineDiscountByType() {
		Map<String, Integer> mapTypeProducts = mapTypeOfProducts();
		for (var productCart : productsCart) {
			defineDiscountByType(productCart, mapTypeProducts);
		}
	}
	
	private void defineDiscountByType(ProductCart productCart, Map<String, Integer> mapTypeProducts) {
		productCart.setPercentualDiscountByType(BigDecimal.ZERO);
		String key = productCart.getProduct().getType();
		if (mapTypeProducts.containsKey(key)) {
			if (mapTypeProducts.get(key).compareTo(Integer.valueOf(10)) >= 0) {
				productCart.setPercentualDiscountByType(BigDecimal.TEN);
				productCart.defineSubtotals();
			}
		}
	}
	
	private Map<String, Integer> mapTypeOfProducts() {
		Map<String, Integer> mapProducts = new HashMap<>();
		for (var productCart : productsCart) {
			String key = productCart.getProduct().getType();
			if (mapProducts.containsKey(key)) {
				mapProducts.put(key, mapProducts.get(key) + productCart.getQuantity());
			} else {
				mapProducts.put(key, productCart.getQuantity());
			}
			productCart.defineSubtotals();
		}
		return mapProducts;
	}

	private void defineDiscountsProgressiveAndCoupon() {
		BigDecimal discountByCoupon = coupon != null ? coupon.getDiscountPercentage() : BigDecimal.ZERO;
		BigDecimal totalValueCart = productsCart.stream().map(p -> p.getSubTotalValue())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		addDiscountsToProducts(defineProgressiveDiscount(totalValueCart), discountByCoupon);
	}
	
	private BigDecimal defineProgressiveDiscount(BigDecimal totalValueCart) {
		BigDecimal progressiveDiscount = BigDecimal.ZERO;
		if (totalValueCart.compareTo(new BigDecimal("1000")) > 0 
				&& totalValueCart.compareTo(new BigDecimal("5000")) <= 0) {
			progressiveDiscount = new BigDecimal("5");
		} else if (totalValueCart.compareTo(new BigDecimal("5000")) > 0 
				&& totalValueCart.compareTo(new BigDecimal("10000")) <= 0) {
			progressiveDiscount = new BigDecimal("7");
		} else if (totalValueCart.compareTo(new BigDecimal("10000")) > 0) {
			progressiveDiscount = new BigDecimal("10");
		}
		return progressiveDiscount;
	}
	
	private void addDiscountsToProducts(BigDecimal progressiveDiscount, BigDecimal discountByCoupon) {
		for (var productCart : productsCart) {
			productCart.setPercentualProgressiveDiscount(progressiveDiscount);
			productCart.setPercentualDiscountByCoupon(discountByCoupon);
			productCart.defineSubtotals();
		}
	}

	public void defineTotals() {
		totalValue = productsCart.stream()
				.map(p -> p.getSubTotalValue())
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(2, RoundingMode.HALF_EVEN);
		totalValueWithDiscount = productsCart.stream()
				.map(p -> p.getSubTotalValueWithDiscount())
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(2, RoundingMode.HALF_EVEN);
	}

}
