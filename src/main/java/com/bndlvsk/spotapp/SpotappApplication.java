package com.bndlvsk.spotapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpotappApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpotappApplication.class, args);
    }

}
