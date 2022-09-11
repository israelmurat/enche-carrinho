package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.muratsystems.enchecarrinho.domain.model.Coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CouponDTO {
	
	private Long id;
	private String code;
	private BigDecimal discountPercentage;
	private LocalDateTime expiration;
	
	public CouponDTO(Coupon coupon) {
		id = coupon.getId();
		code = coupon.getCode();
		discountPercentage = coupon.getDiscountPercentage();
	}
	
}
