package com.muratsystems.enchecarrinho.core;

import java.time.LocalDateTime;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.muratsystems.enchecarrinho.api.dto.CouponDTO;
import com.muratsystems.enchecarrinho.domain.model.Coupon;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
		
		Converter<String, LocalDateTime> converterToEntityDateTimeCoupon = ctx 
				-> ctx.getSource() != null ? LocalDateTime.parse(ctx.getSource()) : null;
		modelMapper.createTypeMap(CouponDTO.class, Coupon.class)
			.addMappings(mapper -> mapper.using(converterToEntityDateTimeCoupon)
					.map(CouponDTO::getExpiration, Coupon::setExpiration));
		
		return modelMapper;
	}
	
}
