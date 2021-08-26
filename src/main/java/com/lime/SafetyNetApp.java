package com.lime;

import com.lime.controller.PersonController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetApp {
    public static void main(String[] args) {
//        PersonController.getPersons();
        SpringApplication.run(SafetyNetApp.class, args);
    }
}
