package com.Satvik.chat.chatapp_backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roomId;

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderBy("timeStamp ASC")
    private List<Message> messages = new ArrayList<>();
}
