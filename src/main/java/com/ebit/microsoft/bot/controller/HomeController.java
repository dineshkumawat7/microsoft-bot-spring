package com.ebit.microsoft.bot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {
    @GetMapping("/status")
    public String getStatus() {
        return "Running..";
    }
}
