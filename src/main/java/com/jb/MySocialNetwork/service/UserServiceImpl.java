package com.jb.MySocialNetwork.service;

import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.exceptions.ErrMsg;
import com.jb.MySocialNetwork.exceptions.SocialNetworkException;
import com.jb.MySocialNetwork.models.Counts;
import com.jb.MySocialNetwork.repos.PostRepository;
import com.jb.MySocialNetwork.repos.UserRepository;
import lombok.RequiredArgsConstructor;
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

        List<User> userFriends = new ArrayList<>(user.getFriends());
        userFriends.add(newFriend);
        user.setFriends(userFriends);
        user.setNumberOfFriends(userFriends.size());
        userRepository.saveAndFlush(user);

        List<User> newFriendFriends = new ArrayList<>(newFriend.getFriends());
        newFriendFriends.add(user);
        newFriend.setNumberOfFriends(newFriendFriends.size());
        newFriend.setFriends(newFriendFriends);
        userRepository.saveAndFlush(newFriend);
        return newFriendId;
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

        List<User> userFriends = new ArrayList<>(user.getFriends());
        userFriends.remove(newFriend);
        user.setNumberOfFriends(userFriends.size());
        user.setFriends(userFriends);
        userRepository.saveAndFlush(user);

        List<User> newFriendFriends = new ArrayList<>(newFriend.getFriends());
        newFriendFriends.remove(user);
        newFriend.setNumberOfFriends(newFriendFriends.size());
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
    public Post increaseLike(long userId, Post post) throws SocialNetworkException {
        System.out.println("*1*");
        User currentUser = userRepository.findById(userId).orElseThrow(() ->
                new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION)
        );
        System.out.println("*2*");
        List<Post> userLikes = currentUser.getLikes();
        System.out.println("*3*");
        System.out.println("current user likes: " + userLikes);

        System.out.println("*4*");
        int likes = post.getLikes();

        System.out.println("*5*");
        List<User> likeUserList = post.getLikeUsersList();
        System.out.println("*6*");
        System.out.println(likeUserList);
        boolean userIdExists = false;

        for (int i = 0; i < likeUserList.size(); i++) {
            System.out.println("*7*");
            if (likeUserList.get(i).getId() == currentUser.getId()) {
                System.out.println("*8*");
                System.out.println("exists");
                userIdExists = true;
            }
        }
//        if (likeUserList.contains(currentUser)) {
//            System.out.println("exists");
//            return null;
//        }
        if (!userIdExists) {
            System.out.println("*9*");
            likes++;
            userLikes.add(post);
            currentUser.setLikes(userLikes);
            userRepository.saveAndFlush(currentUser);
            System.out.println("*10*");
            System.out.println("^^^");
            System.out.println("updated likes of the user^^^" + userRepository.findById(currentUser.getId()).orElseThrow().getLikes());
            System.out.println("^^^");
            likeUserList.add(currentUser);
            post.setLikes(likes);
            post.setLikeUsersList(likeUserList);
            System.out.println(post);
            System.out.println("added!: " + likeUserList);
            System.out.println("*11*");
            postRepository.saveAndFlush(post);
            return post;
        }
        return null;
    }

    @Override
    public List<User> getFiveUsers(long userId) {
        List<User> users = userRepository.getFiveUsersEachTime(0);
        return users;
    }

    @Override
    public List<User> getFiveUsersWithFiveOffset(long userId, int offset) {
        List<User> users = userRepository.getFiveUsersEachTime(offset);
        return users;
    }

    @Override
    public List<User> getFiveNotMyFriends(long userId) {
        List<User> users = userRepository.notMyFriends(0, userId);
        return users;
    }

    @Override
    public List<User> getFiveNotMyFriendsWithFiveOffset(long userId, int offset) {
        List<User> users = userRepository.notMyFriends(offset, userId);
        users.forEach(System.out::println);
        return users;
    }

    @Override
    public List<User> getFiveFriends(long userId) {
        List<User> users = userRepository.myFriends(0, userId);
        return users;
    }

    @Override
    public List<User> getFiveFriendsWithFiveOffset(long userId, int offset) {
        List<User> users = userRepository.myFriends(offset, userId);
        return users;
    }

//    @Override
//    public int numberOfFriendsPosts(long userId) {
//        return userRepository.numberOfFriendsPosts(userId);
//    }
//
//    @Override
//    public int numberOfFriends(long userId) {
//        return userRepository.numberOfFriends(userId);
//    }
//
//    @Override
//    public int numberOfNonFriends(long userId) {
//        return userRepository.numberOfNonFriends(userId);
//    }
//
//    @Override
//    public int numberOfTotalUsers(long userId) {
//        return userRepository.numberOfAllUsers();
//    }

    @Override
    public Counts counts(long userId) {
        List<Integer> numberOfTotalUsers = userRepository.numberOfAllUsers();
        System.out.println(numberOfTotalUsers);
        List<Integer> numberOfFriendsPosts = userRepository.numberOfFriendsPosts(userId);
        System.out.println(numberOfFriendsPosts);
        List<Integer> numberOfFriends = userRepository.numberOfFriends(userId);
        System.out.println(numberOfFriends);
        Counts counts = new Counts(numberOfFriends.get(0), numberOfFriendsPosts.get(0), numberOfTotalUsers.get(0));
        return counts;
    }
}
