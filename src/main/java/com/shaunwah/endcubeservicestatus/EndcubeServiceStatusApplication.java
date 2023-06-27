package com.shaunwah.endcubeservicestatus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EndcubeServiceStatusApplication {

    public static void main(String[] args) {
        SpringApplication.run(EndcubeServiceStatusApplication.class, args);
    }

}
