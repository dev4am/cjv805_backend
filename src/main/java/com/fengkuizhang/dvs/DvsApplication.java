package com.fengkuizhang.dvs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //TODO get rid of spring security
public class DvsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DvsApplication.class, args);
	}

}
