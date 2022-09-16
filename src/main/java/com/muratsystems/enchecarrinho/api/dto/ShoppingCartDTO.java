package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @ToString @EqualsAndHashCode
public class ShoppingCartDTO {

	private List<ProductCartDTO> productsCart = new ArrayList<>();
	private CouponDTO coupon;
	private BigDecimal totalValue;
	private BigDecimal totalValueWithDiscount;

}
