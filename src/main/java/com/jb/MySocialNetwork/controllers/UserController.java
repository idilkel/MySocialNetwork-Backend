package com.jb.MySocialNetwork.controllers;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.exceptions.SocialNetworkException;
import com.jb.MySocialNetwork.exceptions.SocialNetworkSecurityException;
import com.jb.MySocialNetwork.security.TokenManager;
import com.jb.MySocialNetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;
    private final TokenManager tokenManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    long addFriend(@RequestHeader("Authorization") UUID token, long newFriendId) throws SocialNetworkException, SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.addFriend(userId, newFriendId);
    }

    @GetMapping("/posts/mine")
    List<Post> getAllMyPostsDescTimeLimit10(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.getAllMyPostsDescTimeLimit10(userId);
    }

    @GetMapping("/posts/friends")
    List<Post> getAllMyFriendsPostsDescTimeLimit10(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.getAllMyFriendsPostsDescTimeLimit10(userId);
    }

    @DeleteMapping("/friendship")
    void deleteFriend(@RequestHeader("Authorization") UUID token, long friendId) throws SocialNetworkException, SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        userService.deleteFriend(userId, friendId);
    }

    @PutMapping("/picture")
    String addOrUpdatePicture(@RequestHeader("Authorization") UUID token, String picture) throws SocialNetworkException, SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        userService.addOrUpdatePicture(userId, picture);
        return picture;
    }

    @PostMapping("/new-post")
    @ResponseStatus(HttpStatus.CREATED)
    Post addNewPost(@RequestHeader("Authorization") UUID token, @RequestBody Post post) throws SocialNetworkSecurityException, SocialNetworkException {
        long userId = tokenManager.getUserId(token);
        userService.addNewPost(userId, post);
        return post;
    }
}
