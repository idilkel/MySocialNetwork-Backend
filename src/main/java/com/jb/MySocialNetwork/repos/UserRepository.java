package com.jb.MySocialNetwork.repos;

import com.jb.MySocialNetwork.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
