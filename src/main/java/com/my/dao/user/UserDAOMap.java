package com.my.dao.user;

import com.my.entity.User;

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
        User user1 = new User(0, "valera12@gmail.com", "Valera", "Ivanov", "valera");
        User user2 = new User(1, "ivan13@gmail.com", "Ivan", "Petrov", "ivan");
        users.put(user1.getEmail(), user1);
        users.put(user2.getEmail(), user2);
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
