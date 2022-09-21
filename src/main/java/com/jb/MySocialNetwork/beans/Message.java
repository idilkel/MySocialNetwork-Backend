package com.jb.MySocialNetwork.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime time;

    private String message;

    @ManyToOne
    //@JoinColumn(name = "sender")
    private User sender;

    @ManyToOne
    //@JoinColumn(name = "receiver")
    private User receiver;
}
