package com.fastcampus.befinal.presentation.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ServerController {

    @Value("${server.state}")
    private String serverState;

    @GetMapping("/health-check")
    public String healthCheck(){
        return serverState;
    }
}
