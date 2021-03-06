package com.example.restful_sample.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = "password")
//@JsonFilter("UserInfo")
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    @Size(min = 2, message = "이름은 2자 이상이어야 합니다.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String name;
    @Past
    @ApiModelProperty(notes = "사용자의 등록일을 입력해 주세요.")
    private Date joinDate;

    private String password;
    @ApiModelProperty(notes = "사용자의 비밀번호를 입력해 주세요.")
    private String ssn;

}

/**
 * Entity = Create Table
 * create table user (
 * id integer not null,
 * join_date timestamp,
 * name varchar(255),
 * password varchar(255),
 * ssn varchar(20),
 * primary key (id)
 * )
 */
