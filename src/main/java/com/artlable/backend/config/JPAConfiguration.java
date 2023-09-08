package com.artlable.backend.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.artlable.backend")
@EnableJpaRepositories(basePackages = "com.artlable.backend")
public class JPAConfiguration {

}
