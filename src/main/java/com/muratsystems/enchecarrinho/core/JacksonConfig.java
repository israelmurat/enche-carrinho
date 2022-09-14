package com.muratsystems.enchecarrinho.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {

	@Bean
	public ObjectMapper objectMapper() {
		var mapper = new ObjectMapper(); 
		mapper.findAndRegisterModules();
	    return mapper;
	}
	
}
