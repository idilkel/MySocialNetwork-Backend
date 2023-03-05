package com.jb.MySocialNetwork.clr;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.repos.PostRepository;
import com.jb.MySocialNetwork.service.CommentService;
import com.jb.MySocialNetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Order(3)
@RequiredArgsConstructor
public class TestService implements CommandLineRunner {
    private final UserService userService;
    private final PostRepository postRepository;
    private final CommentService commentService;

    @Override
    public void run(String... args) throws Exception {
        userService.addFriend(3L, 5L);
        userService.addFriend(4L, 5L);

        List<Post> user2Posts = userService.getAllMyPostsDescTimeLimit10(2L);
        user2Posts.forEach(System.out::println);
        System.out.println("------");

        List<Post> user5FriendsPost = userService.getAllMyFriendsPostsDescTimeLimit10(5L);
        user5FriendsPost.forEach(System.out::println);
        for (Post p : user5FriendsPost) {
            System.out.println(p);
            long postId = p.getId();
            System.out.println(userService.getPostUserId(5L, postId));
        }

        System.out.println("------");
        userService.deleteFriend(3L, 5L);
        Post post = Post.builder().title("Hello world").story("Look at my new app").picture("https://images.unsplash.com/photo-1555066931-4365d14bab8c").time(LocalDateTime.now()).build();
        userService.addNewPost(3L, post);
        System.out.println("------");
        User userByMail = userService.getUserByMail(2L, "idil@gmail.com");
        System.out.println(userByMail);

        System.out.println("---add comment-------");
        User sender = userService.getUserById(6L);
        String msg1 = "I am soooo happy you are having such a great time!!!";
        String msg2 = "Next time call me too!";
        commentService.addComment(6L, 2L, msg1);
        commentService.addComment(6L, 1L, msg2);
        System.out.println("~~All comments~~~");
        commentService.getAllComments(6L).forEach(System.out::println);
        System.out.println("~~All comments of post#2~~~");
        commentService.getAllCommentsOfOnePost(6L, 1L).forEach(System.out::println);
    }
}
