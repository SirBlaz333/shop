package com.my.dao.user.impl;

import com.my.dao.user.UserDAO;
import com.my.entity.User;
import com.my.entity.UserBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDAOMap implements UserDAO {
    private final Map<String, User> users;
    private static final AtomicInteger ID = new AtomicInteger();

    public UserDAOMap(){
        users = new ConcurrentHashMap<>();
        addDefaultValues();
    }

    private void addDefaultValues(){
        User user = new UserBuilder().
                withEmail("valera12@gmail.com").
                withFirstname("Valera").
                withLastname("Valera").
                withPassword("valera").
                getUser();
        addUser(user);
        user = new UserBuilder().
                withEmail("ivan13@gmail.com").
                withFirstname("Ivan").
                withLastname("Petrov").
                withPassword("ivan").
                getUser();
        addUser(user);
    }

    @Override
    public User addUser(User user) {
        String email = user.getEmail();
        user.setId(ID.incrementAndGet());
        users.put(email, user);
        return user;
    }

    @Override
    public void removeUser(User user) {
        String email = user.getEmail();
        User removedUser = users.remove(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return users.get(email);
    }

    @Override
    public User updateUser(User user) {
        String email = user.getEmail();
        users.put(email, user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
