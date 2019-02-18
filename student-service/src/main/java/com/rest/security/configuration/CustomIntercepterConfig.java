package com.rest.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomIntercepterConfig implements WebMvcConfigurer{
	private CustomReqRespInterceptor customReqRespInterceptor;
	
	public CustomIntercepterConfig(CustomReqRespInterceptor customReqRespInterceptor){
		this.customReqRespInterceptor = customReqRespInterceptor;
	}
	
	@Override
	 public void addInterceptors(InterceptorRegistry registry) {
	  registry.addInterceptor(customReqRespInterceptor).addPathPatterns("/**/student/**");;
	 }
}
