package com.muratsystems.enchecarrinho.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muratsystems.enchecarrinho.model.domain.Coupon;
import com.muratsystems.enchecarrinho.model.domain.Product;
import com.muratsystems.enchecarrinho.model.domain.ShoppingCart;
import com.muratsystems.enchecarrinho.model.repository.CouponRepository;
import com.muratsystems.enchecarrinho.model.repository.ProductRepository;

@RestController
@RequestMapping(value = "/cart")
public class ShoppingCartController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CouponRepository couponRepository;

	@PostMapping(value = "/{idProduct}")
	public String addToChart(@PathVariable("idProduct") Long idProducut, 
			@CookieValue("cart") Optional<String> jsonCart, HttpServletResponse response)
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

		return shoppingCart.toString();

	}

}
