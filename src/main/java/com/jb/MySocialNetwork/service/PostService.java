package com.jb.MySocialNetwork.service;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.exceptions.SocialNetworkException;

import java.util.List;

public interface PostService {
    List<Post> getAllMyPostsDescTimeLimit5(int pageNumber, long userId);

    List<Post> getAllMyFriendsPostsDescTimeLimit5(int pageNumber, long userId);

    List<Post> getFiveMyFriendsPosts(long userId);

    List<Post> getFiveMyFriendsPostsOffset(long userId, int offset);

    Post addNewPost(long userId, Post post) throws SocialNetworkException;


}
