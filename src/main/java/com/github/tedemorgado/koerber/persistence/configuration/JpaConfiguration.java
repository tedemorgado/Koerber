package com.github.tedemorgado.koerber.persistence.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
   basePackages = {"com.github.tedemorgado.koerber.persistence.repository"}
)
public class JpaConfiguration {
}
