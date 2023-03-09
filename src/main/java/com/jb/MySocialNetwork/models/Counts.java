package com.jb.MySocialNetwork.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Counts {
    private int numberOfFriends;
    private int numberOfFriendsPosts;
    private int numberOfTotalUsers;
}
