package com.muratsystems.enchecarrinho.model.domain;

import java.util.ArrayList;
import java.util.List;

import com.muratsystems.enchecarrinho.model.dto.ProductCartDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ShoppingCart {
	
	private List<ProductCartDTO> productsCart = new ArrayList<>();
	private Coupon coupon;

	public void add(Product product, Coupon coupon) {
		productsCart.add(new ProductCartDTO(product));
	}

}
