package com.jb.MySocialNetwork.service;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.exceptions.ErrMsg;
import com.jb.MySocialNetwork.exceptions.SocialNetworkException;
import com.jb.MySocialNetwork.repos.PostRepository;
import com.jb.MySocialNetwork.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Override
    public List<Post> getAllMyPostsDescTimeLimit5(int pageNumber, long userId) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        return postRepository.getAllMyPostsDescTimeLimit5(userId, pageable);
    }

    @Override
    public List<Post> getAllMyFriendsPostsDescTimeLimit5(int pageNumber, long userId) {
        Pageable pageable = PageRequest.of(pageNumber, 5);
        List<Post> posts = postRepository.getAllMyFriendsPostsDescTimeLimit5(userId, pageable);
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

    @Override
    public Post addNewPost(long userId, Post post) throws SocialNetworkException {
        User user = userRepository.findById(userId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        user.setNumberOfPosts(user.getNumberOfPosts() + 1);
        post.setUser(user);
        //The posts are in the posts-repository, and we don't need them additionally in the user-repository
//        List<Post> posts = user.getPosts();
//        posts.add(post);
//        user.setPosts(posts);
        postRepository.save(post);
        userRepository.saveAndFlush(user);
        return post;
    }
}
