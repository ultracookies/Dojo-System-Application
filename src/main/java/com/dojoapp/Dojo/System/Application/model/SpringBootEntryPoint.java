package com.dojoapp.Dojo.System.Application.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootEntryPoint {

    public static void main(String[] args) {
		SpringApplication.run(JavaFXApplication.class, args);
    }

}
