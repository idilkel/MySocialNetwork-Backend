package com.jb.MySocialNetwork.clr;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.service.CommentService;
import com.jb.MySocialNetwork.service.PostService;
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
    private final PostService postService;
    private final CommentService commentService;

    @Override
    public void run(String... args) throws Exception {
        userService.addFriend(3L, 5L);
        userService.addFriend(4L, 5L);

        List<Post> user2Posts = postService.getAllMyPostsDescTimeLimit5(0, 2L);
        user2Posts.forEach(System.out::println);
        System.out.println("------");

        List<Post> user5FriendsPost = postService.getAllMyFriendsPostsDescTimeLimit5(0, 5L);
        user5FriendsPost.forEach(System.out::println);
        for (Post p : user5FriendsPost) {
            System.out.println(p);
            long postId = p.getId();
            System.out.println(userService.getPostUserId(5L, postId));
        }

        System.out.println("------");
        userService.deleteFriend(3L, 5L);
        System.out.println("---Add Posts-----");
        Post post1 = Post.builder().title("Hello world").story("Look at my new app").picture("https://images.unsplash.com/photo-1555066931-4365d14bab8c").time(LocalDateTime.now()).build();
        postService.addNewPost(3L, post1);
        Post post2 = Post.builder().title("The perfect cake").story("I found the perfect cake in a small Patisserie nearby").picture("https://images.unsplash.com/photo-1565958011703-44f9829ba187").time(LocalDateTime.now().minusDays(3).minusHours(5)).build();
        postService.addNewPost(2L, post2);
        Post post3 = Post.builder().title("My favorite Pizza").story("This pizza is wow!!!").picture("https://images.unsplash.com/photo-1565299624946-b28f40a0ae38").time(LocalDateTime.now().minusDays(10).minusHours(1)).build();
        postService.addNewPost(2L, post3);
        Post post4 = Post.builder().title("My cat and my dog").story("Aren't they the cutest").picture("https://images.unsplash.com/photo-1623387641168-d9803ddd3f35").time(LocalDateTime.now().minusMonths(5).minusDays(3).minusHours(5).minusMinutes(23)).build();
        postService.addNewPost(2L, post4);
        Post post5 = Post.builder().title("Thinking about a vacation").story("Does anyone have a recommendation were can I find enough snow at December?").picture("https://images.unsplash.com/photo-1532124957326-34c5605398").time(LocalDateTime.of(2022, 12, 20, 12, 22)).build();
        postService.addNewPost(2L, post5);
        Post post6 = Post.builder().title("I want a dog").story("I think I am going to take it home").picture("https://images.unsplash.com/photo-1593134257782-e89567b7718a").time(LocalDateTime.now().minusMonths(11).minusDays(23).minusHours(7).minusMinutes(44)).build();
        postService.addNewPost(5L, post6);

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

        System.out.println("---Not My friends----");
        userService.getFiveNotMyFriendsWithFiveOffset(2L, 0);
    }
}
