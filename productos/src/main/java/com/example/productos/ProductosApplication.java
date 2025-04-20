package com.example.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry 
public class ProductosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductosApplication.class, args);
    }
}
