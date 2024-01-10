package com.bookmyshow.Book.My.Show.models.dto.request;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AddShowDTO {
    int hours;
    int minutes;
    int ticketPrice;
    UUID movieId;
    UUID hallId;
}
