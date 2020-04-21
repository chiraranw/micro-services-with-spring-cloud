package com.chiraranw.mslibrary.services;

import com.chiraranw.mslibrary.model.Book;
import com.chiraranw.mslibrary.model.Member;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryMemberService {

    private final WebClient.Builder client;

    @Autowired
    public LibraryMemberService(WebClient.Builder client) {
        this.client = client;
    }

    //Members ==============================================
    @HystrixCommand(fallbackMethod = "getMbFb", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds", value = "5000")})
    public Member getMb(Integer mbId) {
        return this.client
                .build()
                .get()
                .uri("http://ms-members-service/lib/v1/mb/" + mbId)
                .retrieve()
                .bodyToMono(Member.class)
                .block();
    }

    public Member getMbFb(Integer mbId) {
        return new Member(mbId, "No Name");
    }

    @HystrixCommand(fallbackMethod = "getMbsFb", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds", value = "5000")})
    public List<Member> getMbs() {
        return this.client
                .build()
                .get()
                .uri("http://ms-members-service/lib/v1/mb/all")
                .retrieve()
                .bodyToFlux(Member.class).toStream().collect(Collectors.toList());
    }

    public List<Member> getMbsFb() {
        return Arrays.asList();
    }


}
