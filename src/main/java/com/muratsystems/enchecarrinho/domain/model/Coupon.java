package com.muratsystems.enchecarrinho.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.muratsystems.enchecarrinho.api.dto.CouponDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Getter
	private Long id;
	
	@Getter @Setter
	private String code;
	
	@Getter @Setter
	private BigDecimal discountPercentage;
	
	@Getter @Setter
	private LocalDateTime expiration;

	public Coupon(CouponDTO couponDTO) {
		code = couponDTO.getCode();
		discountPercentage = couponDTO.getDiscountPercentage();
	}
	
}
