package com.App.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ANTONIO
 */
@RestController
@RequestMapping("/auth")
public class TestAuthController {

    @GetMapping("/get")

    public String hellogET() {
        return "Hola mundo gET";
    }

    @PostMapping("/post")
    public String helloPost() {
        return "Hola mundo Post";
    }

    @PutMapping("/put")
    public String helloPut() {
        return "Hola mundo Put";
    }

    @DeleteMapping("/delete")
    public String helloDelete() {
        return "Hola mundo Delete";
    }

    @PatchMapping("/patch")
    public String helloPatch() {
        return "Hola mundo Patch";
    }
}
