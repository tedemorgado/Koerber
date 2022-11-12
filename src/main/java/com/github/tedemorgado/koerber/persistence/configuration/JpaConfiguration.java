package com.github.tedemorgado.koerber.persistence.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableEnversRepositories
@EnableJpaRepositories(
   basePackages = {"com.github.tedemorgado.koerber.persistence.repository"},
   repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class
)
//@EnableJpaAuditing
public class JpaConfiguration {
}
