package com.muratsystems.enchecarrinho.core;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerFactoryCustomizerConfig {

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
		return (factory) -> factory
				.addContextCustomizers((context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
	}

}
