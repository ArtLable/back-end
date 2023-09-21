package com.artlable.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Primary
    @Bean
    WebClient.Builder webClient() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer ->
                                configurer.defaultCodecs().maxInMemorySize(-1)).build());
    }

}
