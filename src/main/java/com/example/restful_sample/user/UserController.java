package com.example.restful_sample.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    // 의존성 주입
    private UserDaoService userDaoService;

    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userDaoService.findAll();
    }

    // GET /user/1 or /user/2 -> String이지만 int로 자동 맵핑됨
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id) {
        return userDaoService.findOne(id);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
