package com.example.restful_sample.user;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserDaoService {

    private static List<User> usersList = new ArrayList<>();
    private static int usersCount = 3;

    static {
        usersList.add(new User(1, "홍길이", new Date()));
        usersList.add(new User(2, "순딩이", new Date()));
        usersList.add(new User(3, "봉만이", new Date()));
    }

    public List<User> findAll() {
        return usersList;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        usersList.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user : usersList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

}
