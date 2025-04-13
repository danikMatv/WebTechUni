package com.taskforwebtech.taskforwebtech.controller;

import com.taskforwebtech.taskforwebtech.dto.AuthenticationRequest;
import com.taskforwebtech.taskforwebtech.dto.AuthenticationResponse;
import com.taskforwebtech.taskforwebtech.dto.UserRequest;
import com.taskforwebtech.taskforwebtech.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@Valid @RequestBody UserRequest registerRequest){
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        return authenticationService.authenticate(authenticationRequest);
    }
}
