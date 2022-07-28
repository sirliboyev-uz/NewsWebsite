package com.example.newswebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class NewsWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsWebsiteApplication.class, args);
    }

}
