package com.jb.MySocialNetwork.security;

import com.jb.MySocialNetwork.beans.User;
import com.jb.MySocialNetwork.exceptions.SocialNetworkSecurityException;

import java.time.LocalDate;
import java.util.UUID;

public interface LoginManager {
    User register(String firstName, String lastName, String email, String password, LocalDate dob) throws SocialNetworkSecurityException;

    UUID login(String email, String password) throws SocialNetworkSecurityException;
}
