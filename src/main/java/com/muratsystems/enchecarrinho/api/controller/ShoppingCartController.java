package com.muratsystems.enchecarrinho.api.controller;

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
import com.muratsystems.enchecarrinho.api.dto.ShoppingCartDTO;
import com.muratsystems.enchecarrinho.domain.exception.BusinessException;
import com.muratsystems.enchecarrinho.domain.service.CouponService;
import com.muratsystems.enchecarrinho.domain.service.ProductService;

@RestController
@RequestMapping(value = "/cart")
public class ShoppingCartController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CouponService couponService;

	@PostMapping(value = "/{idProduct}")
	public ResponseEntity<ShoppingCartDTO> addToChart(@CookieValue("cart") Optional<String> jsonCart,
			HttpServletResponse response, @PathVariable("idProduct") Long idProducut) throws JsonProcessingException {

		/**
		 * Receber carrinho pelo cookie (JSON) Se não tiver cookie do carrinho, cria
		 * novo carrinho
		 */

		// ObjectMapper é classe do Jackson que desserializa o Json.
		Optional<ShoppingCartDTO> optCart = Optional.ofNullable(getShoppingCartByCookie(jsonCart));
		optCart.get().addProducts(productService.findById(idProducut));

		Cookie cookie = new Cookie("cart", new ObjectMapper().writeValueAsString(optCart.get()));
		cookie.setHttpOnly(true);
		cookie.setPath("/cart");
		response.addCookie(cookie);

		return new ResponseEntity<>(optCart.get(), HttpStatus.OK);

	}

	private ShoppingCartDTO getShoppingCartByCookie(Optional<String> jsonCart) {
		ShoppingCartDTO shoppingCart = jsonCart.map(json -> {
			try {
				return new ObjectMapper().readValue(json, ShoppingCartDTO.class);
			} catch (JsonProcessingException e) {
				throw new RuntimeException();
			}
		}).orElse(new ShoppingCartDTO());
		return shoppingCart;
	}

	@PostMapping(value = "/apply-coupon/{codeCoupon}")
	public ResponseEntity<ShoppingCartDTO> applyCoupon(@CookieValue("cart") Optional<String> jsonCart,
			HttpServletResponse response, @PathVariable("codeCoupon") String codeCoupon)
			throws JsonProcessingException {

		Optional<ShoppingCartDTO> optCart = Optional.ofNullable(getShoppingCartByCookie(jsonCart));
		if (!optCart.isPresent() || optCart.get().getProductsCart().isEmpty()) {
			throw new BusinessException("Não é possível aplicar o cupom com o carrinho vazio!");
		}

		Optional<CouponDTO> optNewCoupon = couponService.findByCode(codeCoupon);
//		if (!optNewCoupon.isPresent() || optNewCoupon.get().getExpiration().isBefore(LocalDateTime.now())) {
		if (!optNewCoupon.isPresent()) {
			throw new BusinessException("Cupom de desconto não cadastrado ou já está expirado!");
		}

		Optional<CouponDTO> optCouponCart = Optional.ofNullable(optCart.get().getCoupon());

		if (optCouponCart.isPresent()) {
			if (optNewCoupon.get().getDiscountPercentage().compareTo(optCouponCart.get().getDiscountPercentage()) > 0) {
				// Retirar desconto e reaplicar
				optCart.get().setCoupon(optNewCoupon.get());
			}
		} else {
			// Aplicar desconto
			optCart.get().setCoupon(optNewCoupon.get());
		}
		optCart.get().defineTotals();

		Cookie cookie = new Cookie("cart", new ObjectMapper().writeValueAsString(optCart.get()));
		cookie.setHttpOnly(true);
		cookie.setPath("/cart");
		response.addCookie(cookie);

		return new ResponseEntity<>(optCart.get(), HttpStatus.OK);

	}

}
