package com.jb.MySocialNetwork.security;

import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.enums.UserType;
import com.jb.MySocialNetwork.exceptions.SecMsg;
import com.jb.MySocialNetwork.exceptions.SocialNetworkSecurityException;
import com.jb.MySocialNetwork.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginManagerImpl implements LoginManager {
    private final UserRepository userRepository;
    private final TokenManager tokenManager;

    @Override
    public User register(String firstName, String lastName, String email, String password, LocalDate dob) throws SocialNetworkSecurityException {
        User user = User.builder().firstName(firstName).lastName(lastName).type(UserType.user).email(email).password(password).dob(dob).build();
        if (userRepository.existsByEmail(email)) {
            throw new SocialNetworkSecurityException(SecMsg.EMAIL_ALREADY_EXISTS);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public UUID login(String email, String password) throws SocialNetworkSecurityException {
        if (!userRepository.existsByEmail(email)) {
            throw new SocialNetworkSecurityException(SecMsg.EMAIL_OR_PASSWORD_DONT_EXIST);
        }
        if (!userRepository.existsByEmailAndPassword(email, password)) {
            throw new SocialNetworkSecurityException(SecMsg.EMAIL_OR_PASSWORD_INCORRECT);
        }
        UUID token = tokenManager.add(email, password);
        return token;
    }
}
