package com.example.restful_sample.user;

import com.example.restful_sample.user.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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

//    // GET /user/1 or /user/2 -> String이지만 int로 자동 맵핑됨
//    @GetMapping("/user/{id}")
//    public User getUser(@PathVariable int id) {
//        User userById = userDaoService.findOne(id);
//        if (userById == null) {
//
//            throw new UserNotFoundException(String.format(("ID[%s] not found"), id));
//
//        }
//
//        return userById;
//    }

    @GetMapping("/user/{id}")
    public ResponseEntity<EntityModel<User>> getUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //Hateoas
        EntityModel<User> entityModel = EntityModel.of(user);

        //WebMvcLinkBuilder
        WebMvcLinkBuilder linkTo =
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());

        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok().body(entityModel);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User newUser = userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // 저장된 user의 id값을 uri로 변경 -> Header에 Location Key로 전달
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id) {
        User userById = userDaoService.deleteById(id);

        if (userById == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }
}
