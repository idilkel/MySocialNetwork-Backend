package com.jb.MySocialNetwork.service;

import com.jb.MySocialNetwork.beans.Post;
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
}
