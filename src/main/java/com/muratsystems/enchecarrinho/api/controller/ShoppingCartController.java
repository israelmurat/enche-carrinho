package com.muratsystems.enchecarrinho.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muratsystems.enchecarrinho.api.dto.CouponDTO;
import com.muratsystems.enchecarrinho.api.dto.ProductCartDTO;
import com.muratsystems.enchecarrinho.domain.model.Coupon;
import com.muratsystems.enchecarrinho.domain.model.Product;
import com.muratsystems.enchecarrinho.domain.model.ShoppingCart;
import com.muratsystems.enchecarrinho.domain.repository.CouponRepository;
import com.muratsystems.enchecarrinho.domain.repository.ProductRepository;

@RestController
@RequestMapping(value = "/cart")
public class ShoppingCartController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CouponRepository couponRepository;

	@PostMapping(value = "/{idProduct}")
	public ResponseEntity<List<ProductCartDTO>> addToChart(@CookieValue("cart") Optional<String> jsonCart, 
			@CookieValue("coupon") Optional<String> jsonCoupon,
			HttpServletResponse response, @PathVariable("idProduct") Long idProducut)
			throws JsonProcessingException {

		/**
		 * Receber carrinho pelo cookie (JSON) Se não tiver cookie do carrinho, cria
		 * novo carrinho
		 */

		// ObjectMapper é classe do Jackson que desserializa o Json.
		ShoppingCart shoppingCart = jsonCart.map(json -> {
			try {
				return new ObjectMapper().readValue(json, ShoppingCart.class);
			} catch (JsonProcessingException e) {
				throw new RuntimeException();
			}
		}).orElse(new ShoppingCart());

//		shoppingCart.add(productRepository.findById(idProducut).get(), couponRepository.findById(idCoupon).get());
		shoppingCart.add(new Product(), new Coupon());
		Cookie cookie = new Cookie("cart", new ObjectMapper().writeValueAsString(shoppingCart));
		cookie.setHttpOnly(true);
		response.addCookie(cookie);

		return new ResponseEntity<>(shoppingCart.getProductsCart(), HttpStatus.OK);

	}
	
	@PostMapping(value = "/apply-coupon/{codeCoupon}")
	public void applyCoupon(@CookieValue("coupon") Optional<String> jsonCoupon,
			HttpServletResponse response, @PathVariable("codeCoupon") String codeCoupon)
			throws JsonProcessingException {
		
		CouponDTO couponDTO = jsonCoupon.map(json -> {
			try {
				return new ObjectMapper().readValue(json, CouponDTO.class);
			} catch (JsonProcessingException e) {
				throw new RuntimeException();
			}
		}).orElse(new CouponDTO());
		
		Cookie cookie = new Cookie("coupon", new ObjectMapper().writeValueAsString(couponDTO));
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		
	}

}
