package com.jb.MySocialNetwork.security;

import com.jb.MySocialNetwork.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class LoginResponse {
    private UUID token;
    private String email;
    //    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private Timestamp loginTime = Timestamp.valueOf(LocalDateTime.now());
    private UserType type;

    public LoginResponse(UUID token, String email, UserType type) {
        this.token = token;
        this.email = email;
        this.type = type;
    }
}
