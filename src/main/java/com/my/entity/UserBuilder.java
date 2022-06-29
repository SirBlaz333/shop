package com.my.entity;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class UserBuilder {
    private final User user;
    public UserBuilder(){
        user = new User();
    }

    public UserBuilder withId(int id){
        user.setId(id);
        return this;
    }

    public UserBuilder withEmail(String email){
        user.setEmail(email);
        return this;
    }

    public UserBuilder withFirstname(String firstname){
        user.setFirstName(firstname);
        return this;
    }

    public UserBuilder withLastname(String lastname){
        user.setLastname(lastname);
        return this;
    }

    public UserBuilder withPassword(String password){
        user.setPassword(password);
        return this;
    }

    public UserBuilder withNewsletter(boolean newsletter){
        user.setNewsletter(newsletter);
        return this;
    }

    public UserBuilder withImage(BufferedImage image){
        user.setImage(image);
        return this;
    }

    public User getUser() {
        return user;
    }
}
