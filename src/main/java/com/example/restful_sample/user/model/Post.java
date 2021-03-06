package com.example.restful_sample.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // Parent(User) : Child(Post) -> 1 : N
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore // 해당 데이터 json에서 숨기기
    private User user;

}
