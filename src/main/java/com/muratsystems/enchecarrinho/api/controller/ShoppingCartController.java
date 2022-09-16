package com.muratsystems.enchecarrinho.api.controller;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muratsystems.enchecarrinho.api.dto.ShoppingCartDTO;
import com.muratsystems.enchecarrinho.domain.exception.CustomRuntimeException;
import com.muratsystems.enchecarrinho.domain.service.ShoppingCartService;

/**
 * @author Israel Murat Corrêa
 * @since 09/2022
 * O carrinho de compras funciona da seguinte forma:
 * Ao adicionar um produto no carrinho, após validações e os devidos cálculos, 
 * o estado do carrinho é armazenado em um cookie no navegador, para que não fique 
 * sob responsabilidade do backend em gerenciar o estado do carrinho.
 * Esta idéia foi tirada do vídeo https://www.youtube.com/watch?v=IG2rMjPMMdk 
 * e adaptada para este projeto.
 * O mesmo conceito foi usado na aplicação do cupom de desconto, onde ao adicionar 
 * ou remover um cupom de desconto, é recuperado o cookie do carrinho de compra
 * e os devidos cálculos são executados para os produtos do carrinho.
 */

@RestController
@RequestMapping(value = "/cart")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@PostMapping(value = "/{idProduct}")
	public ResponseEntity<ShoppingCartDTO> addToCart(@CookieValue("cart") Optional<String> jsonCart,
			HttpServletResponse response, @PathVariable("idProduct") Long idProducut) 
					throws JsonProcessingException {

		var shoppingCart = shoppingCartService
				.addProductToCart(getShoppingCartByCookie(jsonCart), idProducut);
		saveCookieCart(shoppingCart, response);
		return new ResponseEntity<>(shoppingCart, HttpStatus.OK);

	}
	
	@DeleteMapping(value = "/{idProduct}")
	public ResponseEntity<ShoppingCartDTO> removeOfCart(@CookieValue("cart") Optional<String> jsonCart,
			HttpServletResponse response, @PathVariable("idProduct") Long idProducut) 
					throws JsonProcessingException {

		var shoppingCart = shoppingCartService
				.removeProductOfCart(getShoppingCartByCookie(jsonCart), idProducut);
		saveCookieCart(shoppingCart, response);
		return new ResponseEntity<>(shoppingCart, HttpStatus.OK);

	}
	
	@PostMapping(value = "/apply-coupon/{codeCoupon}")
	public ResponseEntity<ShoppingCartDTO> applyCoupon(@CookieValue("cart") Optional<String> jsonCart,
			HttpServletResponse response, @PathVariable("codeCoupon") String codeCoupon)
			throws JsonProcessingException {

		var shoppingCart = shoppingCartService.applyCoupon(getShoppingCartByCookie(jsonCart), codeCoupon);
		saveCookieCart(shoppingCart, response);
		return new ResponseEntity<>(shoppingCart, HttpStatus.OK);

	}
	
	@DeleteMapping(value = "/remove-coupon")
	public ResponseEntity<ShoppingCartDTO> removeCoupon(@CookieValue("cart") Optional<String> jsonCart,
			HttpServletResponse response)
			throws JsonProcessingException {

		var shoppingCart = shoppingCartService.removeCoupon(getShoppingCartByCookie(jsonCart));
		saveCookieCart(shoppingCart, response);
		return new ResponseEntity<>(shoppingCart, HttpStatus.OK);

	}

	private ShoppingCartDTO getShoppingCartByCookie(Optional<String> jsonCart) {
		return jsonCart.map(json -> {
			try {
				return new ObjectMapper().readValue(json, ShoppingCartDTO.class);
			} catch (JsonProcessingException e) {
				throw new CustomRuntimeException("Erro ao recuperar cookie do carrinho de compras!");
			}
		}).orElse(new ShoppingCartDTO());
	}
	
	private void saveCookieCart(ShoppingCartDTO shoppingCart, HttpServletResponse response) {
		try {
			var cookie = new Cookie("cart", new ObjectMapper().writeValueAsString(shoppingCart));
			cookie.setHttpOnly(true);
			cookie.setPath("/cart");
			response.addCookie(cookie);
		} catch (JsonProcessingException e) {
			throw new CustomRuntimeException("Erro ao salvar cookie do carrinho de compras!");
		}
	}

}
