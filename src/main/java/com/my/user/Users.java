package com.my.user;

import com.my.user.User;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private final List<User> userList;

    public Users() {
        userList = new ArrayList<>();
        userList.add(new User("valera12@gmail.com", "Valera", "Ivanov", "valera"));
        userList.add(new User("ivan13@gmail.com", "Ivan", "Petrov", "ivan"));
    }

    public List<User> getUserList() {
        return new ArrayList<>(userList);
    }
}
