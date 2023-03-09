package com.jb.MySocialNetwork.service;

import com.jb.MySocialNetwork.beans.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllMyPostsDescTimeLimit10(long userId);

    List<Post> getAllMyFriendsPostsDescTimeLimit10(long userId);

    List<Post> getFiveMyFriendsPosts(long userId);

    List<Post> getFiveMyFriendsPostsOffset(long userId, int offset);

}
