package com.bookmyshow.Book.My.Show.models.dto.request;

import com.bookmyshow.Book.My.Show.models.Screen;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AddScreenDTO {
    List<Screen>screens;
    UUID hallId;

}
