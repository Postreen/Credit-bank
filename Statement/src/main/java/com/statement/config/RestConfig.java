package com.statement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {

    @Bean(name = "dealClient")
    public RestClient dealRestClient(@Value("${client.deal.url}") String dealBaseUrl) {
        return RestClient.builder()
                .baseUrl(dealBaseUrl)
                .build();
    }
}
