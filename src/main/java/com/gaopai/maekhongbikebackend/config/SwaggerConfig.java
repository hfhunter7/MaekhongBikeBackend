package com.gaopai.maekhongbikebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ComponentScan("com.example.demo.controller")
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/v1/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("TestCenter API")
                .description("Spring Boot REST APIs [http://swagger.io](http://swagger.io)")
                .termsOfServiceUrl("http://localhost:7777")
                .contact("nopparat@9picompany.com")
                .license("Nopparat Mueangkaew License")
                .licenseUrl("nopparat@9picompany.com").version("1").build();
    }
}