package com.my.user;

public class User {
    private final String email;
    private final String firstName;
    private final String lastname;
    private final String password;

    public User(String email, String firstName, String secondName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastname = secondName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }
}
