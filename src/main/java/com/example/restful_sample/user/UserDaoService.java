package com.example.restful_sample.user;


import com.example.restful_sample.user.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {

    private static List<User> usersList = new ArrayList<>();
    private static int usersCount = 3;

    static {
        usersList.add(new User(1, "홍길이", new Date(), "pass1", "1111-2222"));
        usersList.add(new User(2, "순딩이", new Date(), "pass2", "3333-4444"));
        usersList.add(new User(3, "봉만이", new Date(), "pass3", "5555-6666"));
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

    public User deleteById(int id) {

        Iterator<User> iterator = usersList.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }

}
