package com.my.user;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private final List<User> userList;

    public Users() {
        userList = new ArrayList<>();
        userList.add(new User(0, "valera12@gmail.com", "Valera", "Ivanov", "valera"));
        userList.add(new User(1, "ivan13@gmail.com", "Ivan", "Petrov", "ivan"));
    }

    public List<User> getUserList() {
        return new ArrayList<>(userList);
    }
}
