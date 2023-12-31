package com.microservices.employee.service.config;

import com.microservices.employee.service.client.DepartmentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient departmentWebClient(){
        return WebClient.builder()
                .baseUrl("http://department-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public DepartmentClient departmentClient(){
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(departmentWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(DepartmentClient.class);
    }

}
