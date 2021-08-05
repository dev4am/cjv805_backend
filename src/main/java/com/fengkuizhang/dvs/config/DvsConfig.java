package com.fengkuizhang.dvs.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class DvsConfig {

    @Value("${mongodb.url}")
    private String mongoUrl;
    @Value("${web.cors.domain}")
    private String corsDomain;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUrl);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedOrigins(corsDomain);
            }
        };
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "cjv805");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public JWTVerifier jwtVerifier(){
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        return JWT.require(algorithm).build();
    }
}
