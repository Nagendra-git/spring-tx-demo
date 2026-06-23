package com.example.txdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the transaction-propagation demo application.
 *
 * <p>Run with {@code mvn spring-boot:run} (after creating the MySQL schema
 * and updating {@code application.properties} with your credentials), then
 * hit the endpoints exposed by {@code DemoController} to exercise each
 * {@code Propagation} value described on {@code ServiceB}.</p>
 */
@SpringBootApplication
public class TxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxDemoApplication.class, args);
    }
}
