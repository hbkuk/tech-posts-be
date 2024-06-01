package com.techbloghub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TechBlogHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechBlogHubApplication.class, args);
    }

}
