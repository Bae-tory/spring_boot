package com.example.restful_sample.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    @Size(min = 2, message = "이름은 2자 이상이어야 합니다.")
    private String name;
    @Past
    private Date joinDate;

    private String password;
    private String ssn;

}
