package com.jb.MySocialNetwork.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;

    private String lastName;
    @Email
    private String email;
    @Length(min = 4, max = 12)
    private String password;

    private LocalDate dob;

}
