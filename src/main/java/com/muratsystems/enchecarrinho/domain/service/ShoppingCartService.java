package com.muratsystems.enchecarrinho.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muratsystems.enchecarrinho.api.dto.CouponDTO;
import com.muratsystems.enchecarrinho.api.dto.ShoppingCartDTO;
import com.muratsystems.enchecarrinho.domain.exception.BusinessException;
import com.muratsystems.enchecarrinho.domain.model.Coupon;
import com.muratsystems.enchecarrinho.domain.model.ShoppingCart;
import com.muratsystems.enchecarrinho.domain.repository.ProductRepository;

@Service
public class ShoppingCartService {
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public ShoppingCartDTO addProductToCart(ShoppingCartDTO shoppingCartDTO, Long idProducut) {
		var shoppingCart = toShoppingCartEntity(shoppingCartDTO);
		shoppingCart.addProduct(productRepository.findById(idProducut)
				.orElseThrow(() -> new BusinessException("Produto não encontrado!"))); 
		return toShoppingCartDto(shoppingCart);
	}
	
	public ShoppingCartDTO removeProductOfCart(ShoppingCartDTO shoppingCartDTO, Long idProducut) {
		var shoppingCart = toShoppingCartEntity(shoppingCartDTO);
		shoppingCart.removeProduct(idProducut); 
		return toShoppingCartDto(shoppingCart);
	}
	
	public ShoppingCartDTO applyCoupon(ShoppingCartDTO shoppingCartDTO, String codeCoupon) {
		var shoppingCart = toShoppingCartEntity(shoppingCartDTO);
		var newCoupon = toCouponEntity(couponService.findByCode(codeCoupon));
		addCouponToCart(newCoupon, shoppingCart);
		definePercentageCouponToProducts(shoppingCart);
		shoppingCart.defineTotals();
		return toShoppingCartDto(shoppingCart);
	}
	
	private void addCouponToCart(Coupon newCoupon, ShoppingCart shoppingCart) {
		validateCoupon(newCoupon);
		Optional<Coupon> optCouponCart = Optional.ofNullable(shoppingCart.getCoupon());
		if (optCouponCart.isPresent()) {
			if (newCoupon.getDiscountPercentage().compareTo(optCouponCart.get().getDiscountPercentage()) >= 0) {
				shoppingCart.setCoupon(newCoupon);
			} else {
				throw new BusinessException(
						"Novo cupom tem percentual de desconto menor do que o já aplicado anteriormente!");
			}
		} else {
			shoppingCart.setCoupon(newCoupon);
		}
	}
	
	private void validateCoupon(Coupon newCoupon) {
		if (newCoupon.getExpiration().isBefore(LocalDateTime.now())) {
			throw new BusinessException("Cupom de desconto expirado!");
		}
	}
	
	public ShoppingCartDTO removeCoupon(ShoppingCartDTO shoppingCartDTO) {
		if (shoppingCartDTO.getCoupon() == null) {
			throw new BusinessException("Não há nenhum cupom aplicado ao carrinho de compras!");
		}
		var shoppingCart = toShoppingCartEntity(shoppingCartDTO);
		shoppingCart.setCoupon(null);
		definePercentageCouponToProducts(shoppingCart);
		shoppingCart.defineTotals();
		return toShoppingCartDto(shoppingCart);
	}
	
	private void definePercentageCouponToProducts(ShoppingCart shoppingCart) {
		BigDecimal discountByCoupon = shoppingCart.getCoupon() != null 
				? shoppingCart.getCoupon().getDiscountPercentage() : BigDecimal.ZERO;
		for (var productCart : shoppingCart.getProductsCart()) {
			productCart.setPercentualDiscountByCoupon(discountByCoupon);
			productCart.defineSubtotals();
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
