package com.my.user.dao;

import com.my.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserDAOMap implements UserDAO{
    private final Map<String, User> users;
    private static final AtomicLong ID = new AtomicLong();

    public UserDAOMap(){
        users = new ConcurrentHashMap<>();
    }

    @Override
    public User addUser(User user) {
        String email = user.getEmail();
        user.setId(ID.incrementAndGet());
        users.put(email, user);
        return user;
    }

    @Override
    public boolean removeUser(User user) {
        String email = user.getEmail();
        User removedUser = users.remove(email);
        return removedUser != null;
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
