package com.jb.MySocialNetwork.service;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.repos.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;


    @Override
    public List<Post> getAllMyPostsDescTimeLimit10(long userId) {
        Pageable pageable = PageRequest.of(0, 10);
        return postRepository.getAllMyPostsDescTimeLimit10(userId, pageable);
    }

    @Override
    public List<Post> getAllMyFriendsPostsDescTimeLimit10(long userId) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> posts = postRepository.getAllMyFriendsPostsDescTimeLimit10(userId, pageable);
        System.out.println("~~~Get all my friends posts by time~~~");
        posts.forEach(System.out::println);
        return posts;
    }

    @Override
    public List<Post> getFiveMyFriendsPosts(long userId) {
        List<Post> posts = postRepository.myFriendsPosts(0, userId);
        return posts;
    }

    @Override
    public List<Post> getFiveMyFriendsPostsOffset(long userId, int offset) {
        List<Post> posts = postRepository.myFriendsPosts(offset, userId);
        return posts;
    }
}
