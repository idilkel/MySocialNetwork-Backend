package com.jb.MySocialNetwork.repos;

import com.jb.MySocialNetwork.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    User findTop1ByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(value = "select exists (SELECT * FROM `my-social-network`.users_friends where user_id=?1 and friends_id=?2) as res;", nativeQuery = true)
    int existsFriendship(Long userId, Long friendId);

    List<User> findByFirstNameOrLastName(String firstName, String lastName);

    @Query(value = "SELECT * FROM `my-social-network`.users\n" +
            " where id != 1 limit 5 offset :offset", nativeQuery = true)
    List<User> getFiveUsersEachTime(@Param("offset") int offset);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM `my-social-network`.users where id not in(SELECT id FROM `my-social-network`.users_friends uf\n" +
            "join `my-social-network`.users u\n" +
            "on uf.friends_id=u.id\n" +
            "where user_id=:id) and id!=:id and id!=1\n" +
            "limit 5 offset :offset", nativeQuery = true)
    List<User> notMyFriends(@Param("offset") int offset, @Param("id") long id);


    @Query(value = "SELECT id, dob, email, first_name, last_name, number_of_friends, password, picture, type FROM `my-social-network`.users_friends uf\n" +
            "join `my-social-network`.users u\n" +
            "on uf.friends_id=u.id\n" +
            "where user_id=:id \n" +
            "limit 5 offset :offset", nativeQuery = true)
    List<User> myFriends(@Param("offset") int offset, @Param("id") long id);
}
