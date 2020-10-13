package com.xujin.fakereddit.controller;

import com.xujin.fakereddit.dto.RegisterRequest;
import com.xujin.fakereddit.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutoController {

    private final AuthService authService;

    public AutoController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest){
        System.out.println("Here is signing up");
        authService.signUp(registerRequest);
        return new ResponseEntity<>("User Register Successfully", HttpStatus.OK);
    }
}
