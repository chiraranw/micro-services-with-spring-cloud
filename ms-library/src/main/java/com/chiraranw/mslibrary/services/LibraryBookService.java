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
public class LibraryBookService {

    private final WebClient.Builder client;

    @Autowired
    public LibraryBookService(WebClient.Builder client) {
        this.client = client;
    }


    //Books ===============================================
    @HystrixCommand(fallbackMethod = "getBksFallBackMethod", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds", value = "5000")})
    public List<Book> getBks() {
        return this.client
                .build()
                .get()
                .uri("http://ms-books-service/lib/v1/bk/all")
                .retrieve()
                .bodyToFlux(Book.class).toStream().collect(Collectors.toList());
    }

    public List<Book> getBksFallBackMethod() {
        return Arrays.asList();
    }


    @HystrixCommand(fallbackMethod = "getBksReadByMbFallBackMethod", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds", value = "5000")})
    public List<Book> getBksReadByMb(Integer mbId) {
        return this.client
                .build()
                .get()
                .uri("http://ms-books-service/lib/v1/bk/readby/" + mbId)
                .retrieve()
                .bodyToFlux(Book.class).toStream().collect(Collectors.toList());
    }

    public List<Book> getBksReadByMbFallBackMethod(Integer mbId) {
        return Arrays.asList();
    }

    @HystrixCommand(fallbackMethod = "getBkFallBackMethod", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds", value = "5000")})
    public Book getBk(Integer bkId) {
        return this.client
                .build()
                .get()
                .uri("http://ms-books-service/lib/v1/bk/" + bkId)
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }

    public Book getBkFallBackMethod() {
        return new Book();
    }


}
