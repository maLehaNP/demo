package com.example.demo.controller;

import com.example.demo.model.Message;

//import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

//@Slf4j
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
//        log.info("Пользователь отправил GET запрос на /hello");
        return "Привет, мир!";
    }

    @PostMapping("/echo")
    public Message echo(@RequestBody Message input) {
//        log.info("Пользователь отправил POST запрос на /echo");
        return input;
    }
}