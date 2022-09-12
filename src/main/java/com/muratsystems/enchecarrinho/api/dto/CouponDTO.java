package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.muratsystems.enchecarrinho.domain.model.Coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class CouponDTO {
	
	private Long id;
	private String code;
	private BigDecimal discountPercentage;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate expiration;
	
	public CouponDTO(Coupon coupon) {
		id = coupon.getId();
		code = coupon.getCode();
		discountPercentage = coupon.getDiscountPercentage();
	}
	
}
