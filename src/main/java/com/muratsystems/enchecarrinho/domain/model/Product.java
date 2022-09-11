package com.muratsystems.enchecarrinho.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.muratsystems.enchecarrinho.api.dto.ProductDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include
	@Getter
	private Long id;

	@Getter @Setter
	private String description;

	@Getter @Setter
	private BigDecimal unitaryValue;
	
	public Product(ProductDTO productDTO) {
		description = productDTO.getDescription();
		unitaryValue = productDTO.getUnitaryValue();
	}

}
