package com.xujin.fakereddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FakeredditApplication {

    public static void main(String[] args) {
        SpringApplication.run(FakeredditApplication.class, args);
    }

}
