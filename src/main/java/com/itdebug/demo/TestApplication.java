package com.itdebug.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {
    public static void main(String[] args)
    {
        System.setProperty("server.servlet.context-path", "/demo");
        SpringApplication.run(TestApplication.class, args);
    }
}
