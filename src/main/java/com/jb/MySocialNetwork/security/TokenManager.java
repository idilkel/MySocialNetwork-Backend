package com.jb.MySocialNetwork.security;

import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.enums.UserType;
import com.jb.MySocialNetwork.exceptions.SecMsg;
import com.jb.MySocialNetwork.exceptions.SocialNetworkSecurityException;
import com.jb.MySocialNetwork.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenManager {
    private final UserRepository userRepository;
    private final Map<UUID, Information> map;

    public UUID add(String email, String password) {
        User userDB;

        if (email.equals("admin@admin.com") && password.equals("admin")) {
            userDB = userRepository.findById(1L).orElseThrow();
        } else {
            userDB = userRepository.findTop1ByEmail(email);
        }

        long userId = userDB.getId();
        removePreviousInstances(userId);

        Information information = new Information();
        information.setUserId(userId);
        information.setType(userDB.getType());
        information.setEmail(email);
        information.setTime(LocalDateTime.now());

        UUID token = UUID.randomUUID();
        map.put(token, information);
        return token;
    }

    public void removePreviousInstances(long userId) {
        map.entrySet().removeIf(ins -> ins.getValue().getUserId() == userId);
    }

    public long getUserId(UUID token) throws SocialNetworkSecurityException {
        Information information = map.get(token);
        if (information == null) {
            throw new SocialNetworkSecurityException(SecMsg.INVALID_TOKEN);
        }
        return information.getUserId();
    }

    public UserType getType(UUID token) throws SocialNetworkSecurityException {
        Information information = map.get(token);
        if (information == null) {
            throw new SocialNetworkSecurityException(SecMsg.INVALID_TOKEN);
        }
        return information.getType();
    }

    //Delete expired tokens every 30 minutes
//    @Scheduled(fixedRate = 1000 * 60)
//    public void deleteExpiredTokenOver30Minutes() {
//        map.entrySet().removeIf(ins -> ins.getValue().getTime().isBefore(LocalDateTime.now().minusMinutes(30)));
//    }

    //at midnight remove all expired 30 min tokens which are not from the current day
    @Scheduled(cron = "0 0 0 * * *", zone = "GMT+2.00")
    public void deleteExpiredTokenOver30Minutes() {
        map.entrySet().removeIf(ins -> ins.getValue().getTime().isBefore(LocalDateTime.now().minusMinutes(30)));
    }

}
