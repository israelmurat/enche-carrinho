package com.muratsystems.enchecarrinho.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.muratsystems.enchecarrinho.api.dto.CouponDTO;
import com.muratsystems.enchecarrinho.domain.exception.CustomRuntimeException;
import com.muratsystems.enchecarrinho.domain.model.Coupon;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
		
		try {
			Converter<String, LocalDateTime> converterToEntityDateTimeCoupon = ctx 
					-> ctx.getSource() != null ? LocalDateTime.parse(ctx.getSource()) : null;
			modelMapper.createTypeMap(CouponDTO.class, Coupon.class)
				.addMappings(mapper -> mapper.using(converterToEntityDateTimeCoupon)
						.map(CouponDTO::getExpiration, Coupon::setExpiration));
		} catch (DateTimeParseException e) {
			throw new CustomRuntimeException("Data do cupom inválida!");
		}
		
		return modelMapper;
	}
	
}
