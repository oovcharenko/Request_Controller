package ru.web.request_controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RequestControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestControllerApplication.class, args);
        System.out.println("Контроллер запущен");
    }

}
