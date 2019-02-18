package com.rest.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class SpringZipkinServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringZipkinServerApplication.class, args);
	}

}
