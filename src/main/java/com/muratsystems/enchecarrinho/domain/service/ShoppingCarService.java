package com.muratsystems.enchecarrinho.domain.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muratsystems.enchecarrinho.api.dto.CouponDTO;
import com.muratsystems.enchecarrinho.api.dto.ShoppingCartDTO;
import com.muratsystems.enchecarrinho.domain.exception.BusinessException;
import com.muratsystems.enchecarrinho.domain.model.Coupon;
import com.muratsystems.enchecarrinho.domain.model.Product;
import com.muratsystems.enchecarrinho.domain.model.ShoppingCart;

@Service
public class ShoppingCarService {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CouponService couponService;

	public void addProductToCart(ShoppingCartDTO shoppingCartDTO, Long idProducut) {
		var shoppingCart = new ShoppingCart(shoppingCartDTO);
		shoppingCart.addProducts(new Product(productService.findById(idProducut)));
		shoppingCartDTO = new ShoppingCartDTO(shoppingCart);
	}
	
	public void applyCoupon(ShoppingCartDTO shoppingCartDTO, String codeCoupon) {
		
		var shoppingCart = new ShoppingCart(shoppingCartDTO);

		Optional<CouponDTO> optNewCoupon = couponService.findByCode(codeCoupon);
//		if (!optNewCoupon.isPresent() || optNewCoupon.get().getExpiration().isBefore(LocalDateTime.now())) {
//			throw new BusinessException("Cupom de desconto não cadastrado ou já está expirado!");
//		}
		if (!optNewCoupon.isPresent()) {
			throw new BusinessException("Cupom de desconto não cadastrado!");
		}
		var newCoupon = new Coupon(optNewCoupon.get());
		if (newCoupon.getExpiration().isBefore(LocalDateTime.now())) {
			throw new BusinessException("Cupom de desconto expirado!");
		}

		Optional<Coupon> optCouponCart = Optional.ofNullable(shoppingCart.getCoupon());

		if (optCouponCart.isPresent()) {
			if (newCoupon.getDiscountPercentage().compareTo(optCouponCart.get().getDiscountPercentage()) > 0) {
				// Retirar desconto e reaplicar
				shoppingCart.setCoupon(newCoupon);
			}
		} else {
			// Aplicar desconto
			shoppingCart.setCoupon(newCoupon);
		}
		shoppingCart.defineTotals();
		
		shoppingCartDTO = new ShoppingCartDTO(shoppingCart);
		
	}
	
}
