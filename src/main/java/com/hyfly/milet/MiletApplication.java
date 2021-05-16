package com.hyfly.milet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MiletApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiletApplication.class, args);
    }

}
