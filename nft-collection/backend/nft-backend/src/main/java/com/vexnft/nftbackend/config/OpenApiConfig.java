package com.vexnft.nftbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI vexNftOpenAPI() {

		return new OpenAPI().info(new Info().title("VEX NFT Collection API").version("1.0.0")
				.description("REST API for reading the VEX NFT Collection deployed on Arbitrum One."));
	}
}