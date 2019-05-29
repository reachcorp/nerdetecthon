package com.reachcorp.reach.nerdetecthon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class NERdetecthonApplication {

    public static void main(String[] args) {
        SpringApplication.run(NERdetecthonApplication.class, args);
    }
}
