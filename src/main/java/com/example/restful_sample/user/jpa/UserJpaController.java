package com.example.restful_sample.user.jpa;

import com.example.restful_sample.user.User;
import com.example.restful_sample.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id); // 존재할수도 있고 아닐수도 있어서 Optional<T>

        if (!user.isPresent()) { // 존재하지 않는다면
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user.get();
    }

    @GetMapping("/user/link/{id}")
    public ResponseEntity<EntityModel<User>> getUserEntity(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) { // 존재하지 않는다면
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder linkTo =
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());

        entityModel.add(linkTo.withRel("all-users-jpa"));

        return ResponseEntity.ok().body(entityModel);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // 저장된 user의 id값을 uri로 변경 -> Header에 Location Key로 전달
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
