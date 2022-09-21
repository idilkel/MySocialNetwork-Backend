package com.jb.MySocialNetwork.clr;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.repos.PostRepository;
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
        Post post = Post.builder().title("Hello world").story("Look at my new app").picture("AppPic").time(LocalDateTime.now()).build();
        userService.addNewPost(3L, post);
        System.out.println("------");
    }
}
