package com.jb.MySocialNetwork.controllers;

import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.enums.UserType;
import com.jb.MySocialNetwork.exceptions.SocialNetworkSecurityException;
import com.jb.MySocialNetwork.security.LoginManager;
import com.jb.MySocialNetwork.security.LoginRequest;
import com.jb.MySocialNetwork.security.LoginResponse;
import com.jb.MySocialNetwork.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("api/welcome")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WelcomeController {
    private final LoginManager loginManager;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User Register(@Valid @RequestBody RegisterRequest registerRequest) throws SocialNetworkSecurityException {
        String firstName = registerRequest.getFirstName();
        String lastName = registerRequest.getLastName();
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        LocalDate dob = registerRequest.getDob();
        return loginManager.register(firstName, lastName, email, password, dob);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) throws SocialNetworkSecurityException {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        UserType type = loginRequest.getType();
        UUID token = loginManager.login(email, password);
        return new LoginResponse(token, email, type);
    }
}
