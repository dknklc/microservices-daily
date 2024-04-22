package com.dekankilic.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport(){ // In real projects, you may have some complex fallback requirements like triggering an email to the support team or sending some default response, so it is up to your requirements.
        return Mono.just("An error occurred. Please try after some time or contact support team!!!");
    }
}
