package com.jb.MySocialNetwork.service;

import com.jb.MySocialNetwork.beans.Comment;
import com.jb.MySocialNetwork.beans.Post;
import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.exceptions.ErrMsg;
import com.jb.MySocialNetwork.exceptions.SocialNetworkException;
import com.jb.MySocialNetwork.repos.CommentRepository;
import com.jb.MySocialNetwork.repos.PostRepository;
import com.jb.MySocialNetwork.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //not to be used in the web layer - because it gets the comments of all users
    @Override
    public List<Comment> getAllComments(long userId) {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getAllCommentsOfOnePost(long userId, long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public Comment addComment(long userId, long postId, String msg) throws SocialNetworkException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        User sender = userRepository.findById(userId).orElseThrow(() -> new SocialNetworkException(ErrMsg.ID_DOES_NOT_EXIST_EXCEPTION));
        Comment comment = Comment.builder().sender(sender).time(LocalDateTime.now()).message(msg).post(post).build();
        commentRepository.save(comment);
        return comment;
    }
}
