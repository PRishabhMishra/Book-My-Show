package com.bookmyshow.Book.My.Show.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String name;
    String address;
    @Column(unique = true)
    String email;
    long contactNumber;
    String password;
    @OneToMany(mappedBy = "hall")
    List<Screen> screens;
    @OneToMany(mappedBy = "hall")
    List<Show>shows;
    @OneToMany(mappedBy = "hall")
    List<Ticket>tickets;



}
