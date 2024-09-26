package com.task.task_submission_service.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Bean("taskServiceWebClient")
    public WebClient getTaskServiceWebClient( @Value("${taskService.url}") String BASE_URI){

        HttpAsyncClientBuilder httpAsyncClientBuilder=HttpAsyncClientBuilder.create();
        httpAsyncClientBuilder.setProxy(new HttpHost("localhost",5003))
                              .setDefaultRequestConfig(RequestConfig.custom()
                                      .setConnectionRequestTimeout(5000, TimeUnit.MILLISECONDS)
                                       .setResponseTimeout(4000,TimeUnit.MILLISECONDS).build());

        return WebClient.builder().baseUrl(BASE_URI)
                .clientConnector(new HttpComponentsClientHttpConnector(httpAsyncClientBuilder.build()))
                .build();
    }

    @Bean("loginServiceWebClient")
    public WebClient getLoginWebClient(@Value("${loginService.url}") String BASE_URI){
        HttpAsyncClientBuilder httpAsyncClientBuilder=HttpAsyncClientBuilder.create();
        httpAsyncClientBuilder.setProxy(new HttpHost("Localhost",5001))
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectionRequestTimeout(5000,TimeUnit.MILLISECONDS)
                        .setResponseTimeout(4000,TimeUnit.MILLISECONDS).build());

        return WebClient.builder().baseUrl(BASE_URI)
                .clientConnector(new HttpComponentsClientHttpConnector(httpAsyncClientBuilder.build()))
                .build();
    }



}
