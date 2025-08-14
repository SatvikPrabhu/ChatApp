package com.Satvik.chat.chatapp_backend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class HelloController {

    @GetMapping("/Hello")
    public String hello(){
        return "Hello World!";
    }
}
