package com.jb.MySocialNetwork.security;

import com.jb.MySocialNetwork.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @Email
    private String email;
    @Length(min = 4, max = 12)
    private String password;

    private UserType type;
}
