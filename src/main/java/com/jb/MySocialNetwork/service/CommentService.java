package com.jb.MySocialNetwork.service;

import com.jb.MySocialNetwork.beans.Comment;
import com.jb.MySocialNetwork.exceptions.SocialNetworkException;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments(long userId);

    List<Comment> getAllCommentsOfOnePost(long userId, long postId);

    Comment addComment(long userId, long postId, String msg) throws SocialNetworkException;


}
