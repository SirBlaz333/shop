package com.my.entity;

public class User {
    private int id;
    private String email;
    private String firstName;
    private String lastname;
    private String password;
    private boolean newsletter;

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

    public int getId() {
        return id;
    }

    public boolean getNewsletter() {
        return newsletter;
    }

    public void setId(int id) {
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

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }
}
