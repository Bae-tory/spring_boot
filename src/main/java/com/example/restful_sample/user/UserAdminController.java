package com.example.restful_sample.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserAdminController {

    // 의존성 주입
    private UserDaoService userDaoService;

    public UserAdminController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public MappingJacksonValue getAllUsers() {
        List<User> userList = userDaoService.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "ssn");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userList);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    //     GET /user/1 or /user/2 -> String이지만 int로 자동 맵핑됨  -> /admin/v1/users/1
//    @GetMapping(value = "/user/{id}", params = "version=1")
//    @GetMapping("/v1/user/{id}")
//    @GetMapping(value = "/user/{id}", headers = "API_VERSION=1")
    @GetMapping(value = "/user/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue/*filter에 걸리기 위해*/ getUserV1(@PathVariable int id) {
        User userById = userDaoService.findOne(id);
        if (userById == null) {
            throw new UserNotFoundException(String.format(("ID[%s] not found"), id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "ssn");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userById);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    //    @GetMapping(value = "/user/{id}", params = "version=1")
//    @GetMapping("/v2/user/{id}")
//    @GetMapping(value = "/user/{id}", headers = "API_VERSION=2")
    @GetMapping(value = "/user/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue/*filter에 걸리기 위해*/ getUserV2(@PathVariable int id) {
        User userById = userDaoService.findOne(id);
        if (userById == null) {
            throw new UserNotFoundException(String.format(("ID[%s] not found"), id));
        }

        // User를 UserV2로 복사
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(userById, userV2); // id, name, joinDate, password, ssn
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filterV2 = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filterV2);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
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
