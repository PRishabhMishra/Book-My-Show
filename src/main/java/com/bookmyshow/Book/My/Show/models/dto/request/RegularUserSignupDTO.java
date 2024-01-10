package com.bookmyshow.Book.My.Show.models.dto.request;

import com.bookmyshow.Book.My.Show.enums.UserType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RegularUserSignupDTO {

    String name;
    String email;
    long phoneNumber;
    String password;
    UserType type;//movieOwners,HallOwners and RegularUser
    int age;
}
