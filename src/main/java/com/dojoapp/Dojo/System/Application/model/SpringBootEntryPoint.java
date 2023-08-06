package com.dojoapp.Dojo.System.Application.model;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootEntryPoint {

    public static void main(String[] args) {
		Application.launch(JavaFXApplication.class, args);
    }

}
