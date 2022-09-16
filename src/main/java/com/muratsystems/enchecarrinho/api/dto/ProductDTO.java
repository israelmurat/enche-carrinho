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
public class ProductDTO {

	private Long id;
	
	@NotBlank(message = "A descrição é obrigatória!")
	@Length(max = 255, message = "A descrição deve ter no máximo {max} caracteres!")
	private String description;
	
	@NotBlank(message = "Tipo do produto não informado!")
	@Length(max = 255, message = "O tipo do produto deve ter no máximo {max} caracteres!")
	private String type;
	
	@NotNull(message = "Valor unitário não informado!")
	private BigDecimal unitaryValue;
	
}
