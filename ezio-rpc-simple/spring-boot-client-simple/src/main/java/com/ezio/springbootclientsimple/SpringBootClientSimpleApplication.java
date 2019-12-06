package com.ezio.springbootclientsimple;

import annotation.EnableServiceDiscovery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableServiceDiscovery
public class SpringBootClientSimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootClientSimpleApplication.class, args);
    }

}
