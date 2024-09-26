package com.task.api_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackControllers {

    @GetMapping("user-service-fallback")
    public Mono<String> userServiceFallback(){
        return Mono.just("Currently User Service is not available please try after some time");
    }

    @GetMapping("task-service-fallback")
    public Mono<String> taskServiceFallback(){
        return Mono.just("Currently Task Service is not available please try after some time");
    }

    @GetMapping("submission-service-fallback")
    public Mono<String> submissionServiceFallback(){
        return Mono.just("Currently Submission Service is not available please try after some time");
    }
}
