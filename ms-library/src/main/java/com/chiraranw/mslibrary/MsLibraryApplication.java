package com.chiraranw.mslibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class MsLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsLibraryApplication.class, args);
    }

    //Beans
    @LoadBalanced
    @Bean
    WebClient.Builder getWebClient() {
        return WebClient.builder();
    }


}
