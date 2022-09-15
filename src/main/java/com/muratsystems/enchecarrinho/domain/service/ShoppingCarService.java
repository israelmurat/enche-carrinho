package com.muratsystems.enchecarrinho.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muratsystems.enchecarrinho.api.dto.CouponDTO;
import com.muratsystems.enchecarrinho.api.dto.ShoppingCartDTO;
import com.muratsystems.enchecarrinho.domain.exception.BusinessException;
import com.muratsystems.enchecarrinho.domain.model.Coupon;
import com.muratsystems.enchecarrinho.domain.model.ProductCart;
import com.muratsystems.enchecarrinho.domain.model.ShoppingCart;
import com.muratsystems.enchecarrinho.domain.repository.ProductRepository;

@Service
public class ShoppingCarService {
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public ShoppingCartDTO addProductToCart(ShoppingCartDTO shoppingCartDTO, Long idProducut) {
		var shoppingCart = toShoppingCartEntity(shoppingCartDTO);
		
		/////
		shoppingCart.addProducts(productRepository.findById(idProducut).orElseThrow()); //// ver
		/////
		
		return toShoppingCartDto(shoppingCart);
	}
	
	public ShoppingCartDTO applyCoupon(ShoppingCartDTO shoppingCartDTO, String codeCoupon) {
		
		var shoppingCart = toShoppingCartEntity(shoppingCartDTO);
		var newCoupon = toCouponEntity(couponService.findByCode(codeCoupon));
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
		definePercentageCouponToProducts(shoppingCart);
		shoppingCart.defineTotals();
		
		return toShoppingCartDto(shoppingCart);
		
	}
	
	private void definePercentageCouponToProducts(ShoppingCart shoppingCart) {
		BigDecimal discountByCoupon = shoppingCart.getCoupon() != null 
				? shoppingCart.getCoupon().getDiscountPercentage() : BigDecimal.ZERO;
		for (var productCart : shoppingCart.getProductsCart()) {
			productCart.setPercentualDiscountByCoupon(discountByCoupon);
		}
	}
	
	private ShoppingCart toShoppingCartEntity(ShoppingCartDTO shoppingCartDTO) {
		return modelMapper.map(shoppingCartDTO, ShoppingCart.class);
	}
	
	private ShoppingCartDTO toShoppingCartDto(ShoppingCart shoppingCart) {
		return modelMapper.map(shoppingCart, ShoppingCartDTO.class);
	}
	
	private Coupon toCouponEntity(CouponDTO couponDTO) {
		return modelMapper.map(couponDTO, Coupon.class);
	}
	
}
