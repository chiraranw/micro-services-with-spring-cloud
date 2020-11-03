package com.chiraranw.msmembersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsMembersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsMembersServiceApplication.class, args);
    }

}
