package com.task.task_service.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Bean("taskWebClient")
    public WebClient taskUserService(@Value("${task.serviceUrl}")String url){

        HttpAsyncClientBuilder httpAsyncClientBuilder=HttpAsyncClientBuilder.create();
        httpAsyncClientBuilder.setProxy(new HttpHost("localhost",5001))
                               .setDefaultRequestConfig(RequestConfig.custom()
                                 .setConnectionRequestTimeout(5000, TimeUnit.MICROSECONDS)
                                 .setResponseTimeout(4000,TimeUnit.MILLISECONDS).build());

        return WebClient.builder().baseUrl(url)
                .clientConnector(new HttpComponentsClientHttpConnector(httpAsyncClientBuilder.build()))
                .build();
    }
}
