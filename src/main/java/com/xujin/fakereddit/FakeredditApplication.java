package com.xujin.fakereddit;

import com.xujin.fakereddit.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class FakeredditApplication {

    public static void main(String[] args) {
        SpringApplication.run(FakeredditApplication.class, args);
    }

}
