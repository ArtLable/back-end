package com.artlable.backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.artlable.backend")
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }

}
