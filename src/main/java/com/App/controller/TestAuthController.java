
package com.App.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ANTONIO
 */
@RestController
@RequestMapping("/auth")
public class TestAuthController {
    
    @GetMapping("/hello")
    public String hello(){
     return "Hola mundo";
    }
    
    @GetMapping("/hello-secured")
    public String helloSecured(){
     return "Hola mundo segurida";
    }
}
