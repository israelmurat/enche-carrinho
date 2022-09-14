package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class CouponDTO {
	
	private Long id;
	private String code;
	private BigDecimal discountPercentage;
	private String expiration;
	
//	public CouponDTO(Coupon coupon) {
//		id = coupon.getId();
//		code = coupon.getCode();
//		discountPercentage = coupon.getDiscountPercentage();
//		expiration = coupon.getExpiration().toString();
//	}
	
}
