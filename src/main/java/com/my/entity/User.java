package com.my.entity;

public class User {
    private long id;
    private String email;
    private String firstName;
    private String lastname;
    private String password;

    public User(long id, String email, String firstName, String secondName, String password) {
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
