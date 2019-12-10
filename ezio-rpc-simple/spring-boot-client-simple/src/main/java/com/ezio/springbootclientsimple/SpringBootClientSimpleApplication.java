package com.ezio.springbootclientsimple;

import annotation.EnableServiceDiscovery;
import com.ezio.server.api.UserService;
import com.ezio.server.dto.UserDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import proxy.ServiceProxyHandler;

@EnableServiceDiscovery
@SpringBootApplication
public class SpringBootClientSimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootClientSimpleApplication.class, args);
    }


    @Bean
    public CommandLineRunner runner(ServiceProxyHandler serviceProxyHandler) {


        return (String... args) -> {
            UserService userService = serviceProxyHandler.serviceProxy("simple-server", UserService.class);
            UserDTO dto = userService.getById(1);
            System.out.println(dto);

        };

    }
}
