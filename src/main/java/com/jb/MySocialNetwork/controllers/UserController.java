package com.jb.MySocialNetwork.controllers;

import com.jb.MySocialNetwork.beans.Comment;
import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.exceptions.SocialNetworkException;
import com.jb.MySocialNetwork.exceptions.SocialNetworkSecurityException;
import com.jb.MySocialNetwork.security.TokenManager;
import com.jb.MySocialNetwork.service.CommentService;
import com.jb.MySocialNetwork.service.PostService;
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
    private final CommentService commentService;
    private final PostService postService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    long addFriend(@RequestHeader("Authorization") UUID token, long newFriendId) throws SocialNetworkException, SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.addFriend(userId, (long) newFriendId);
    }

    @GetMapping("/posts/mine")
    List<Post> getAllMyPostsDescTimeLimit5(@RequestHeader("Authorization") UUID token, @RequestParam(value = "page", required = false) int pageNumber) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return postService.getAllMyPostsDescTimeLimit5(pageNumber, userId);
    }

    @GetMapping("/posts/friends")
    List<Post> getAllMyFriendsPostsDescTimeLimit5(@RequestHeader("Authorization") UUID token, @RequestParam(value = "page", required = false) int pageNumber) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return postService.getAllMyFriendsPostsDescTimeLimit5(pageNumber, userId);
    }

    @DeleteMapping("/friendship")
    void deleteFriend(@RequestHeader("Authorization") UUID token, long friendId) throws SocialNetworkException, SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        userService.deleteFriend(userId, (long) friendId);
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
        postService.addNewPost(userId, post);
        return post;
    }

    @GetMapping("/mail")
    User getUserByMail(@RequestHeader("Authorization") UUID token, String email) throws SocialNetworkSecurityException, SocialNetworkException {
        long userId = tokenManager.getUserId(token);
        User user = userService.getUserByMail(userId, email);
        return user;
    }

    @GetMapping("/name")
    List<User> getUserByName(@RequestHeader("Authorization") UUID token, String name) throws SocialNetworkSecurityException, SocialNetworkException {
        long userId = tokenManager.getUserId(token);
        List<User> users = userService.getUserByFirstNameOrLastName(userId, name);
        return users;
    }

    @PostMapping("/{postId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    Comment addNewComment(@RequestHeader("Authorization") UUID token, @PathVariable long postId, @RequestBody String msg) throws SocialNetworkSecurityException, SocialNetworkException {
        long userId = tokenManager.getUserId(token);
        Comment comment = commentService.addComment(userId, postId, msg);
        return comment;
    }

//    @GetMapping("/comments")
//    List<User> getAllComments(@RequestHeader("Authorization") UUID token, String name) throws SocialNetworkSecurityException, SocialNetworkException {
//        long userId = tokenManager.getUserId(token);
//        List<User> users = userService.getUserByFirstNameOrLastName(userId, name);
//        return users;
//    }

    @GetMapping("/{postId}/comment")
    List<Comment> getAllCommentsOfOnePost(@RequestHeader("Authorization") UUID token, @PathVariable long postId) throws SocialNetworkSecurityException, SocialNetworkException {
        long userId = tokenManager.getUserId(token);
        List<Comment> comments = commentService.getAllCommentsOfOnePost(userId, postId);
        return comments;
    }

//    @PostMapping("/increase-like")
//    @ResponseStatus(HttpStatus.CREATED)
//    void increaseLike(@RequestHeader("Authorization") UUID token, @RequestBody Post post) throws SocialNetworkSecurityException, SocialNetworkException {
//        long userId = tokenManager.getUserId(token);
//        userService.increaseLike(userId, post);
//    }

    @PostMapping("/increase-like")
    @ResponseStatus(HttpStatus.CREATED)
    void increaseLike(@RequestHeader("Authorization") UUID token, @RequestBody Post post) throws SocialNetworkSecurityException, SocialNetworkException {
        long userId = tokenManager.getUserId(token);
        userService.increaseLike(userId, post);
    }

    @GetMapping("/first-five")
    List<User> getFiveUsers(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.getFiveUsers(userId);
    }

    @GetMapping("/next-five/{offset}")
    List<User> getNextFiveUsers(@RequestHeader("Authorization") UUID token, @PathVariable int offset) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.getFiveUsersWithFiveOffset(userId, offset);
    }

    @GetMapping("/meet/first-five")
    List<User> getNonFriends(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.getFiveNotMyFriends(userId);
    }

    @GetMapping("/meet/next-five/{offset}")
    List<User> getNextFiveNonFriends(@RequestHeader("Authorization") UUID token, @PathVariable int offset) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.getFiveNotMyFriendsWithFiveOffset(userId, offset);
    }

    @GetMapping("/friends/first-five")
    List<User> getFiveFriends(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.getFiveFriends(userId);
    }

    @GetMapping("/friends/next-five/{offset}")
    List<User> getNextFiveFriends(@RequestHeader("Authorization") UUID token, @PathVariable int offset) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return userService.getFiveFriendsWithFiveOffset(userId, offset);
    }

    @GetMapping("/friends/posts/first-five")
    List<Post> getFiveFriendsPosts(@RequestHeader("Authorization") UUID token) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return postService.getFiveMyFriendsPosts(userId);
    }

    @GetMapping("/friends/posts/next-five/{offset}")
    List<Post> getNextFiveFriendsPosts(@RequestHeader("Authorization") UUID token, @PathVariable int offset) throws SocialNetworkSecurityException {
        long userId = tokenManager.getUserId(token);
        return postService.getFiveMyFriendsPostsOffset(userId, offset);
    }
}
