package com.programmingtechie.orderservice.controller;

import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory",fallbackMethod = "fallbackMethod")
    public CompletableFuture<ResponseEntity<String>> placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(orderService.placeOrder(orderRequest),
                HttpStatus.CREATED));
    }

    public CompletableFuture<ResponseEntity<String>> fallbackMethod(OrderRequest orderRequest, RuntimeException e){
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>("Something went wrong, please order after some time!",
                HttpStatus.SERVICE_UNAVAILABLE));
    }
}
