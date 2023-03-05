package com.jb.MySocialNetwork.repos;

import com.jb.MySocialNetwork.beans.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Like, Integer> {

    @Query(value = "select exists (SELECT liking_user_id FROM `my-social-network`.likes lk\n" +
            "where liking_user_id=:userId)  as liked", nativeQuery = true)
    int likeExists2(@Param("userId") long userId);

    @Query(value = "select exists (SELECT * FROM `my-social-network`.likes\n" +
            "where liking_user_id = :userId and post_id =:postId) as like_exists", nativeQuery = true)
    int likeExists(@Param("userId") long userId, @Param("postId") long postId);
}
