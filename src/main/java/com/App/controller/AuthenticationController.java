
package com.App.controller;

import com.App.controller.dto.AuthCreateUserRequest;
import com.App.controller.dto.AuthLoginRequest;
import com.App.controller.dto.AuthResponse;
import com.App.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ANTONIO
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
    
    @PostMapping("/log-in")
    public ResponseEntity< AuthResponse> login( @RequestBody @Valid AuthLoginRequest userRequest){       
    
        return new ResponseEntity<>(this.userDetailServiceImpl.loginUser(userRequest),HttpStatus.OK); 
    }
    
    
    @PostMapping("/sign-up")
    public  ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUser){
       
      return  new ResponseEntity<>(this.userDetailServiceImpl.createUser(authCreateUser),HttpStatus.OK);
    }
}
