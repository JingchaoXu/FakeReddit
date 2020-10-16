package com.xujin.fakereddit.controller;

import com.xujin.fakereddit.dto.AuthenticationResponse;
import com.xujin.fakereddit.dto.LoginRequest;
import com.xujin.fakereddit.dto.RefreshTokenRequest;
import com.xujin.fakereddit.dto.RegisterRequest;
import com.xujin.fakereddit.service.AuthService;
import com.xujin.fakereddit.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AutoController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public AutoController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest){
        System.out.println("Here is signing up");
        authService.signUp(registerRequest);
        return new ResponseEntity<>("User Register Successfully", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
         return authService.login(loginRequest);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return  authService.refreshToken(refreshTokenRequest);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>("You have successfully log out",HttpStatus.OK);
    }
}
