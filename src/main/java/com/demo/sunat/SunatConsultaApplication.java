package com.demo.sunat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SunatConsultaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SunatConsultaApplication.class, args);
    }
}
