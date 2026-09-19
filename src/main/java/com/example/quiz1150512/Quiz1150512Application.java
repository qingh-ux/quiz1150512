package com.example.quiz1150512;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {
    UserDetailsServiceAutoConfiguration.class,
    SecurityAutoConfiguration.class
})
public class Quiz1150512Application {

    public static void main(String[] args) {
        SpringApplication.run(Quiz1150512Application.class, args);
    }

}