package com.lime;

import com.lime.domain.Station;
import com.lime.repository.StationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetApp {
    public static void main(String[] args) {
        SpringApplication.run(SafetyNetApp.class, args);
    }
}
