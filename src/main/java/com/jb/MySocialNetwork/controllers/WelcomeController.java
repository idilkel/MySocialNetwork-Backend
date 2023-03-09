package com.jb.MySocialNetwork.controllers;

import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.exceptions.SocialNetworkSecurityException;
import com.jb.MySocialNetwork.models.Counts;
import com.jb.MySocialNetwork.security.*;
import com.jb.MySocialNetwork.service.UserService;
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
    private final UserService userService;
    private final TokenManager tokenManager;

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
        //UserType type = loginRequest.getType();
        UUID token = loginManager.login(email, password);
        //return new LoginResponse(token, email, type);
        return new LoginResponse(token, email);
    }

//    @GetMapping("/friends/posts/number")
//    int getNumberOfFriendsPosts(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
//        long userId = tokenManager.getUserId(token);
//        return userService.numberOfFriendsPosts(userId);
//    }
//
//    @GetMapping("/friends/number")
//    int getNumberOfFriends(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
//        long userId = tokenManager.getUserId(token);
//        return userService.numberOfFriends(userId);
//    }
//
//    @GetMapping("/non-friends/number")
//    int getNumberOfNonFriends(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
//        long userId = tokenManager.getUserId(token);
//        return userService.numberOfNonFriends(userId);
//    }
//
//    @GetMapping("/total/number")
//    int getNumberOfTotalUsers(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
//        long userId = tokenManager.getUserId(token);
//        return userService.numberOfTotalUsers(userId);
//    }

    @GetMapping("/counts")
    Counts getAllCounts(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.counts(userId);
    }

}
