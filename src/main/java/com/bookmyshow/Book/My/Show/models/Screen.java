package com.bookmyshow.Book.My.Show.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String screenName;
    @ManyToOne
    Hall hall;
    int screenCapacity;
    boolean status;//booked or empty
    String type;//  1D or 2D

}
