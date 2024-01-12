package com.bookmyshow.Book.My.Show.models;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Schema(description = "This represents hall model")

public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY,example = "123")
    UUID id;
    String name;
    String address;
    @Column(unique = true)
    String email;
    long contactNumber;
    String password;
    @ManyToOne
    ApplicationUser owner;
    @OneToMany(mappedBy = "hall")
    List<Screen> screens;
    @OneToMany(mappedBy = "hall")
    List<Show>shows;
    @OneToMany(mappedBy = "hall")
    List<Ticket>tickets;



}
