package com.daoki.basic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CheckController {

    @RequestMapping("/hi")
    public String index() {
        return "hi";
    }
}
