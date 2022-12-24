package com.example.mtservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MtServiceApplication.class, args);
    }

}
