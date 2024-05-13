package com.carbontest.treasuremap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;

import com.carbontest.treasuremap.utils.ConfigLoader;

@SpringBootApplication
public class TreasuremapApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreasuremapApplication.class, args);
	}
	
	@Bean
	@Profile("default")
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public ConfigLoader ConfigLoaderForRun(@Value("${spring.map.cfg.run}") Resource resourceFile) {
	    return new ConfigLoader(resourceFile);
	}
	
	@Bean
	@Profile("test")
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public ConfigLoader ConfigLoaderForTest(@Value("${spring.map.cfg.test}") Resource resourceFile) {
	    return new ConfigLoader(resourceFile);
	}
}
