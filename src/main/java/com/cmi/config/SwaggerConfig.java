package com.cmi.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author David Wang
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.cmi.controller")).paths(PathSelectors.ant("/**")).build()
				.apiInfo(apiInfo());
		return docket;
	}

	/**
	 * @return
	 */
	private ApiInfo apiInfo() {

		return new ApiInfo("CMI-SWAGGER REST API", "API for client side consuming with rest", "V1.0", "T&C",
				new Contact("Wang,David", "www.baidu.com", "XXX@gmail.com"), "License of MIT", "www.MIT.com",
				new ArrayList());
	}

}
