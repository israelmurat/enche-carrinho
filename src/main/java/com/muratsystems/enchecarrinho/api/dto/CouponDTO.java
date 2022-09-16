package com.muratsystems.enchecarrinho.api.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter 
@NoArgsConstructor @ToString @EqualsAndHashCode
public class CouponDTO {
	
	private Integer id;
	
	@NotBlank(message = "O código é obrigatório")
	@Length(max = 255, message = "O código deve ter no máximo {max} caracteres!")
	private String code;
	
	@NotNull(message = "Percentual do desconto não foi informado!")
	private BigDecimal discountPercentage;
	
	@NotBlank(message = "Data da expiração deve ser informada no formato yyyy-MM-ddTHH:mm:ss")
	private String expiration;
	
}
