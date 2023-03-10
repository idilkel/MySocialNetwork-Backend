package com.jb.MySocialNetwork.service;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.exceptions.SocialNetworkException;
import com.jb.MySocialNetwork.models.Counts;

import java.util.List;

public interface UserService {

    long addFriend(long userId, long newFriendId) throws SocialNetworkException;


    void deleteFriend(long userId, long newFriendId) throws SocialNetworkException;

    String addOrUpdatePicture(long userId, String picture) throws SocialNetworkException;

    long getPostUserId(long userId, long postId);


    User getUserByMail(long userId, String email) throws SocialNetworkException;

    User getUserById(long userId) throws SocialNetworkException;

    List<User> getUserByFirstNameOrLastName(long userId, String name) throws SocialNetworkException;

    Post getOnePostByPostId(long userId, long postId) throws SocialNetworkException;

    Post increaseLike(long userId, Post post) throws SocialNetworkException;

    List<User> getFiveUsers(long userId);

    List<User> getFiveUsersWithFiveOffset(long userId, int offset);

    List<User> getFiveNotMyFriends(long userId);

    List<User> getFiveNotMyFriendsWithFiveOffset(long userId, int offset);

    List<User> getFiveFriends(long userId);

    List<User> getFiveFriendsWithFiveOffset(long userId, int offset);

//    int numberOfFriendsPosts(long userId);
//
//    int numberOfFriends(long userId);
//
//    int numberOfNonFriends(long userId);
//
//    int numberOfTotalUsers(long userId);

    Counts counts(long userId);
}
