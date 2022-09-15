package com.muratsystems.enchecarrinho.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Getter @Setter
	private Long id;

	@Getter @Setter
	@Column(unique = true, nullable = false)
	private String description;
	
	@Getter @Setter
	@Column(nullable = false)
	private String type;

	@Getter @Setter
	@Column(nullable = false)
	private BigDecimal unitaryValue;

}
