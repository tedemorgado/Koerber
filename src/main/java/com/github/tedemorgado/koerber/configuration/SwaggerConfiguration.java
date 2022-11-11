package com.github.tedemorgado.koerber.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${info.name}") final String name,
                                 @Value("${spring.application.majorVersion}") final String version,
                                 @Value("${info.description}") final String description) {
        return new OpenAPI()
                .info(new Info()
                        .title(name)
                        .description(description)
                        .version(version));
    }
}
