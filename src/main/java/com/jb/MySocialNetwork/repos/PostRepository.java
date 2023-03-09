package com.jb.MySocialNetwork.repos;

import com.jb.MySocialNetwork.beans.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    @Query(value = "SELECT * FROM `my-social-network`.posts where user_id=:userId order by time desc limit 10", nativeQuery = true)
//    List<Post> getAllMyPostsDescTimeLimit10(@Param("userId") Long userId);
//
//    @Query(value = "SELECT * FROM `my-social-network`.users_friends uf\n" +
//            "join `my-social-network`.posts p \n" +
//            "on uf.friends_id=p.user_id\n" +
//            "where uf.user_id=:userId order by time desc limit 10;", nativeQuery = true)
//    List<Post> getAllMyFriendsPostsDescTimeLimit10(@Param("userId") Long userId);

//    @Query(value = "SELECT * FROM `my-social-network`.posts where user_id=?1 order by time desc limit 10", nativeQuery = true)
//    List<Post> getAllMyPostsDescTimeLimit10(Long userId);
//
//    @Query(value = "SELECT * FROM `my-social-network`.users_friends uf\n" +
//            "join `my-social-network`.posts p \n" +
//            "on uf.friends_id=p.user_id\n" +
//            "where uf.user_id=?1 order by time desc limit 10;", nativeQuery = true)
//    List<Post> getAllMyFriendsPostsDescTimeLimit10(Long userId);

    @Query(value = "SELECT * FROM `my-social-network`.posts where user_id=?1 order by time desc", nativeQuery = true)
    List<Post> getAllMyPostsDescTimeLimit10(Long userId, Pageable pageable);

//    @Query(value = "SELECT id,picture,story,time,title,friends_id as user_id FROM `my-social-network`.users_friends uf\n" +
//            "join `my-social-network`.posts p \n" +
//            "on uf.friends_id=p.user_id\n" +
//            "where uf.user_id=?1 order by time desc", nativeQuery = true)
//    List<Post> getAllMyFriendsPostsDescTimeLimit10(Long userId, Pageable pageable);

//    This query gives double results - corrected below
//    @Query(value = "SELECT * FROM `my-social-network`.users_friends uf\n" +
//            "join `my-social-network`.posts p \n" +
//            "on uf.friends_id=p.user_id\n" +
//            "where uf.friends_id=:userId order by time desc", nativeQuery = true)
//    List<Post> getAllMyFriendsPostsDescTimeLimit10(@Param("userId") long userId, Pageable pageable);

    @Query(value = "SELECT distinct p.id, p.likes, p.picture, p.story, p.time, p.title, p.user_id \n" +
            "FROM `my-social-network`.users_friends uf\n" +
            "            join `my-social-network`.posts p \n" +
            "            on uf.friends_id=p.user_id\n" +
            "            where uf.friends_id=:userId  order by time desc", nativeQuery = true)
    List<Post> getAllMyFriendsPostsDescTimeLimit10(@Param("userId") long userId, Pageable pageable);


    @Query(value = "SELECT user_id FROM `my-social-network`.posts where id=?1", nativeQuery = true)
    long getUserIdOfPost(long postId);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM `my-social-network`.posts where user_id in (SELECT id FROM `my-social-network`.users_friends uf\n" +
            "join `my-social-network`.users u\n" +
            "on uf.friends_id=u.id\n" +
            "where user_id=:userId )\n" +
            "limit 5 offset :offset", nativeQuery = true)
    List<Post> myFriendsPosts(@Param("offset") int offset, @Param("userId") long userId);
}
