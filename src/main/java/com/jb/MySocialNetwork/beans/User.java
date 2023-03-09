package com.jb.MySocialNetwork.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jb.MySocialNetwork.enums.UserType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String firstName;

    @Column(nullable = false, length = 45)
    private String lastName;

    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Column(nullable = false, length = 45)
    @Length(min = 4, max = 12)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType type;

    private LocalDate dob;

    private String picture;

    @OneToMany(mappedBy = "user")
    @Singular
    //@ToString.Exclude//May be omitted
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @Singular
    @ToString.Exclude
    @JsonIgnore
    private List<User> friends = new ArrayList<>();

    private int numberOfFriends;


}
