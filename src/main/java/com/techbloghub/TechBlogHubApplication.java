package com.techbloghub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class TechBlogHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechBlogHubApplication.class, args);
    }

}

@RestController
@RequestMapping("/api/health")
class Health {

    @GetMapping
    String health() {
        return "UP";
    }

}

