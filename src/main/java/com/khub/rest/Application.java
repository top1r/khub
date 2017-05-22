package com.khub.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.AsyncRestTemplate;

@SpringBootApplication
public class Application {

    @Value("${timeout}") private Integer timeout;
    @Value("${corePoolSize}") private Integer corePoolSize;
    @Value("${maxPoolSize}") private Integer maxPoolSize;
    @Value("${queueCapacity}") private Integer queueCapacity;
    @Value("${threadPrefix}") private String threadPrefix;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    AsyncRestTemplate asyncRestTemplate(){
        return new AsyncRestTemplate(getHttpRequestFactory(getExecutor()));
    }

    @Bean
    public static PropertyPlaceholderConfigurer properties(){
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[ ]
                { new ClassPathResource( "service.properties" ) };
        ppc.setLocations( resources );
        ppc.setIgnoreUnresolvablePlaceholders( true );
        return ppc;
    }

    private ThreadPoolTaskExecutor getExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadPrefix);
        executor.initialize();
        return executor;
    }

    private SimpleClientHttpRequestFactory getHttpRequestFactory(ThreadPoolTaskExecutor executor){
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setTaskExecutor(executor);
        simpleClientHttpRequestFactory.setConnectTimeout(timeout);
        simpleClientHttpRequestFactory.setReadTimeout(timeout);
        return simpleClientHttpRequestFactory;
    }
}
