package com.example.restful_sample.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = "password")
public class User {

    private Integer id;
    @Size(min = 2, message = "이름은 2자 이상이어야 합니다.")
    private String name;
    @Past
    private Date joinDate;

//    @JsonIgnore // 필드단위
    private String password;
//    @JsonIgnore // 필드단위
    private String ssn;

}
