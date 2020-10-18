package com.epolsoft.wtr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                   .pathMapping("/")
                   .select()
                   .apis(RequestHandlerSelectors.basePackage("com.epolsoft.wtr"))
                   .paths(regex("/.*"))
                   .build().apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Swagger Documentation",
                "Swagger for WTR_Lite",
                "1.0",
                "Terms of Service",
                "",
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html");

        return apiInfo;
    }

}
