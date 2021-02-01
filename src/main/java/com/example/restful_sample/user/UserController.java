package com.example.restful_sample.user;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // 의존성 주입
    private UserDaoService userDaoService;

    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

}
