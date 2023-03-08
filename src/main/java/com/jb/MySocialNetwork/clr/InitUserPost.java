package com.jb.MySocialNetwork.clr;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.enums.UserType;
import com.jb.MySocialNetwork.repos.PostRepository;
import com.jb.MySocialNetwork.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
public class InitUserPost implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        User admin = User.builder().firstName("admin").lastName("admin").type(UserType.admin).email("admin@admin.com").password("admin").build();
        User user1 = User.builder().firstName("Idil").lastName("Kasuto Kelson").email("idil@gmail.com").password("1234").type(UserType.user).dob(LocalDate.of(1972, 11, 29)).picture("https://images.unsplash.com/photo-1619895862022-09114b41f16f").build();
        User user2 = User.builder().firstName("Ido").lastName("Kelson").email("ido@gmail.com").password("1234").type(UserType.user).dob(LocalDate.of(1971, 9, 25)).picture("https://images.unsplash.com/photo-1603415526960-f7e0328c63b1").build();
        User user3 = User.builder().firstName("Eithan").lastName("Kelson").email("eithan@gmail.com").password("1234").type(UserType.user).dob(LocalDate.of(2002, 9, 13)).picture("https://images.unsplash.com/photo-1457449940276-e8deed18bfff").build();
        User user4 = User.builder().firstName("Ben").lastName("Kelson").email("ben@gmail.com").password("1234").type(UserType.user).dob(LocalDate.of(2006, 12, 10)).picture("https://images.unsplash.com/photo-1552374196-c4e7ffc6e126").build();
        User user5 = User.builder().firstName("Moshe").lastName("Cohen").email("moshe@gmail.com").password("1234").type(UserType.user).dob(LocalDate.of(1948, 5, 14)).picture("https://images.unsplash.com/photo-1504257432389-52343af06ae3").build();
        User user6 = User.builder().firstName("Jonathan").lastName("Moor").email("jonathan@gmail.com").password("1234").type(UserType.user).dob(LocalDate.of(1997, 2, 28)).picture("https://images.unsplash.com/photo-1506794778202-cad84cf45f1d").build();
        User user7 = User.builder().firstName("Yana").lastName("Kolog").email("yana@gmail.com").password("1234").type(UserType.user).dob(LocalDate.of(2001, 7, 12)).picture("https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e").build();


        //Arrays.asList is an unmodifiable collection = java.util.ImmutableCollections.uoe exception
        //If added with Arrays.asList - new List should be prepared on modification
        user2.setFriends(Arrays.asList(user1, user3));
        user2.setNumberOfFriends(user2.getNumberOfFriends() + 2);
        //user1.setFriends(List.of(user2));
        List<User> user1Friends = new ArrayList<>();
        user1Friends.add(user2);
        user1.setFriends(user1Friends);
        user1.setNumberOfFriends(user1.getNumberOfFriends() + 1);
        System.out.println("*");

        user3.setFriends(List.of(user2));
        user3.setNumberOfFriends(user3.getNumberOfFriends() + 1);
        userRepository.saveAll(Arrays.asList(admin, user1, user2, user3, user4, user5, user6, user7));

        System.out.println(user4);
        List<User> user4Friends = new ArrayList<>(user4.getFriends());
        System.out.println("User4Friends: " + user4Friends);
        user4Friends.add(user1);
        user4.setNumberOfFriends(user4.getNumberOfFriends() + 1);
        System.out.println("User4Friends: " + user4Friends);
        user4.setFriends(user4Friends);
        userRepository.saveAndFlush(user4);

        List userFriends = user1.getFriends();
        userFriends.add(user4);
        user1.setNumberOfFriends(user1.getNumberOfFriends() + 1);
        user1.setFriends(user1Friends);
        userRepository.saveAndFlush(user1);

        Post newPost1 = Post.builder().user(user1).title("My vacation in Venice").picture("https://i.imgur.com/f5md403.jpg").story("We are having a great time").time(LocalDateTime.now().minusDays(20)).build();
        postRepository.save(newPost1);
//        user1 = userRepository.findById(1L).orElseThrow();
//        List<Post> userPosts = new ArrayList<>(user1.getPosts());
//        userPosts.add(newPost1);
//        user1.setPosts(userPosts);
//        userRepository.saveAndFlush(user1);

        Post newPost2 = Post.builder().user(user1).title("My vacation in Paris").picture("https://i.imgur.com/HdJRCRq.jpg").story("Paris is beautiful").time(LocalDateTime.now().minusDays(2)).build();
        postRepository.save(newPost2);

        Post newPost3 = Post.builder().user(user3).title("My cakes").picture("https://images.unsplash.com/photo-1602351447937-745cb720612f").story("I baked a delicious cake").time(LocalDateTime.now().minusHours(3)).build();
        postRepository.save(newPost3);

        Post newPost5 = Post.builder().user(user5).title("I love Israel").picture("https://i.imgur.com/WkBkr16.jpg").story("We have a beautiful country").time(LocalDateTime.now().minusMonths(2).minusSeconds(15)).build();
        postRepository.save(newPost5);

        System.out.println("~~~~~");
        System.out.println(newPost1 + " " + newPost1.getUser().getId());
        System.out.println(newPost2 + " " + newPost2.getUser().getId());
        System.out.println(newPost3 + " " + newPost3.getUser().getId());
        System.out.println(newPost5 + " " + newPost5.getUser().getId());
        System.out.println("~~~~~");
    }
}
