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
import com.muratsystems.enchecarrinho.api.dto.ShoppingCartDTO;
import com.muratsystems.enchecarrinho.domain.exception.ValidationException;
import com.muratsystems.enchecarrinho.domain.service.ShoppingCartService;

@RestController
@RequestMapping(value = "/cart")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@PostMapping(value = "/{idProduct}")
	public ResponseEntity<ShoppingCartDTO> addToCart(@CookieValue("cart") Optional<String> jsonCart,
			HttpServletResponse response, @PathVariable("idProduct") Long idProducut) throws JsonProcessingException {

		/**
		 * Receber carrinho pelo cookie (JSON) Se não tiver cookie do carrinho, cria
		 * novo carrinho
		 */

		// ObjectMapper é classe do Jackson que desserializa o Json.
		Optional<ShoppingCartDTO> optCart = Optional.ofNullable(getShoppingCartByCookie(jsonCart));
		var shoppingCart = shoppingCartService.addProductToCart(optCart.get(), idProducut);
		
		saveCookieCart(shoppingCart, response);
		return new ResponseEntity<>(shoppingCart, HttpStatus.OK);

	}

	private ShoppingCartDTO getShoppingCartByCookie(Optional<String> jsonCart) {
		var shoppingCart = jsonCart.map(json -> {
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
			throw new ValidationException("Não é possível aplicar o cupom com o carrinho vazio!");
		}
		
		var shoppingCart = shoppingCartService.applyCoupon(optCart.get(), codeCoupon);
		
		saveCookieCart(shoppingCart, response);
		return new ResponseEntity<>(shoppingCart, HttpStatus.OK);

	}
	
	private void saveCookieCart(ShoppingCartDTO shoppingCart, HttpServletResponse response) throws JsonProcessingException {
		var cookie = new Cookie("cart", new ObjectMapper().writeValueAsString(shoppingCart));
		cookie.setHttpOnly(true);
		cookie.setPath("/cart");
		response.addCookie(cookie);
	}

}
