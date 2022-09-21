package com.jb.MySocialNetwork.security;

import com.jb.MySocialNetwork.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Information {
    private long userId;
    private UserType type;
    private LocalDateTime time;
    private String email;
}
