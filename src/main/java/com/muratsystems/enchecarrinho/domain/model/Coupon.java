package com.muratsystems.enchecarrinho.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor @ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Getter @Setter
	private Long id;
	
	@Getter @Setter
	@Column(unique = true, nullable = false)
	private String code;
	
	@Setter
	@Column(nullable = false)
	private BigDecimal discountPercentage;
	
	@Getter @Setter
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private LocalDateTime expiration;

	public BigDecimal getDiscountPercentage() {
		if (discountPercentage != null)
			return discountPercentage;
		else
			return BigDecimal.ZERO;
	}
	
}
