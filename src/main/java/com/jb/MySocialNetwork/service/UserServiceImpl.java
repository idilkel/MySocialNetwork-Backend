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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public long addFriend(long userId, long newFriendId) throws SocialNetworkException {
        if (userId == 1 || newFriendId == 1) {
            throw new SocialNetworkException(ErrMsg.CANNOT_ADD_OR_REMOVE_USER1);
        }
        if (userRepository.existsFriendship(userId, newFriendId) == 1) {
            throw new SocialNetworkException(ErrMsg.FRIENDSHIP_ALREADY_EXISTS);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        User newFriend = userRepository.findById(newFriendId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));

        List userFriends = new ArrayList<>(user.getFriends());
        userFriends.add(newFriend);
        user.setFriends(userFriends);
        userRepository.saveAndFlush(user);

        List newFriendFriends = new ArrayList<>(newFriend.getFriends());
        newFriendFriends.add(user);
        newFriend.setFriends(newFriendFriends);
        userRepository.saveAndFlush(newFriend);
        return newFriendId;
    }

    @Override
    public List<Post> getAllMyPostsDescTimeLimit10(long userId) {
        Pageable pageable = PageRequest.of(0, 10);
        return postRepository.getAllMyPostsDescTimeLimit10(userId, pageable);
    }

    @Override
    public List<Post> getAllMyFriendsPostsDescTimeLimit10(long userId) {
        Pageable pageable = PageRequest.of(0, 10);
        return postRepository.getAllMyFriendsPostsDescTimeLimit10(userId, pageable);
    }

    @Override
    public void deleteFriend(long userId, long newFriendId) throws SocialNetworkException {
        if (userId == 1 || newFriendId == 1) {
            throw new SocialNetworkException(ErrMsg.CANNOT_ADD_OR_REMOVE_USER1);
        }
        if (userRepository.existsFriendship(userId, newFriendId) == 0) {
            throw new SocialNetworkException(ErrMsg.FRIENDSHIP_DOESNT_EXIST);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        User newFriend = userRepository.findById(newFriendId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));

        List userFriends = new ArrayList<>(user.getFriends());
        userFriends.remove(newFriend);
        user.setFriends(userFriends);
        userRepository.saveAndFlush(user);

        List newFriendFriends = new ArrayList<>(newFriend.getFriends());
        newFriendFriends.remove(user);
        newFriend.setFriends(newFriendFriends);
        userRepository.saveAndFlush(newFriend);
    }

    @Override
    public String addOrUpdatePicture(long userId, String picture) throws SocialNetworkException {
        User user = userRepository.findById(userId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        user.setPicture(picture);
        userRepository.saveAndFlush(user);
        return picture;
    }

    @Override
    public long getPostUserId(long userId, long postId) {
        return postRepository.getUserIdOfPost(postId);
    }

    @Override
    public Post addNewPost(long userId, Post post) throws SocialNetworkException {
        User user = userRepository.findById(userId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        post.setUser(user);
        //The posts are in the posts-repository, and we don't need them additionally in the user-repository
//        List<Post> posts = user.getPosts();
//        posts.add(post);
//        user.setPosts(posts);
        postRepository.save(post);
        return post;
    }

    @Override
    public User getUserByMail(long userId, String email) throws SocialNetworkException {
        return userRepository.findByEmail(email).orElseThrow(() -> new SocialNetworkException(ErrMsg.EMAIL_DOES_NOT_EXIST_EXCEPTION));
    }

    @Override
    public User getUserById(long userId) throws SocialNetworkException {
        return userRepository.findById(userId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
    }

    @Override
    public List<User> getUserByFirstNameOrLastName(long userId, String name) throws SocialNetworkException {
        return userRepository.findByFirstNameOrLastName(name, name);
    }

    @Override
    public Post getOnePostByPostId(long userId, long postId) throws SocialNetworkException {
        return postRepository.findById(postId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
    }


    @Override
    public void increaseLike(long userId, Post post) throws SocialNetworkException {
        int likes = post.getLikes();
        List<User> likeUserList = post.getLikeUsersList();
        User currentUser = userRepository.findById(userId).orElseThrow(() ->
                new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION)
        );
        if (likeUserList.contains(currentUser)) {
            return;
        }
        likes++;
        likeUserList.add(currentUser);
        post.setLikes(likes);
        post.setLikeUsersList(likeUserList);
        postRepository.saveAndFlush(post);
    }
}
