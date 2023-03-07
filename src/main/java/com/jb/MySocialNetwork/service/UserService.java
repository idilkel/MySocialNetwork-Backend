package com.jb.MySocialNetwork.service;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.exceptions.SocialNetworkException;

import java.util.List;

public interface UserService {

    long addFriend(long userId, long newFriendId) throws SocialNetworkException;

    List<Post> getAllMyPostsDescTimeLimit10(long userId);

    List<Post> getAllMyFriendsPostsDescTimeLimit10(long userId);

    void deleteFriend(long userId, long newFriendId) throws SocialNetworkException;

    String addOrUpdatePicture(long userId, String picture) throws SocialNetworkException;

    long getPostUserId(long userId, long postId);

    Post addNewPost(long userId, Post pot) throws SocialNetworkException;

    User getUserByMail(long userId, String email) throws SocialNetworkException;

    User getUserById(long userId) throws SocialNetworkException;

    List<User> getUserByFirstNameOrLastName(long userId, String name) throws SocialNetworkException;

    Post getOnePostByPostId(long userId, long postId) throws SocialNetworkException;

    void increaseLike(long userId, Post post) throws SocialNetworkException;
}
