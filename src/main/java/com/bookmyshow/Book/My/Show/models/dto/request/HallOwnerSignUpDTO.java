package com.bookmyshow.Book.My.Show.models.dto.request;

import com.bookmyshow.Book.My.Show.enums.UserType;
import com.bookmyshow.Book.My.Show.models.Hall;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class HallOwnerSignUpDTO {

    String name;
    String email;
    long phoneNumber;
    String password;
    String  type;//movieOwners,HallOwners and RegularUser
    List<Hall> halls;
    int companyAge;
}
