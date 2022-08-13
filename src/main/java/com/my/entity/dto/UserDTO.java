package com.my.entity.dto;

import com.my.entity.user.User;

public class UserDTO {
    private User user;
    private int userRoleId;

    public UserDTO(User user, int userRoleId){
        this.user = user;
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }
}
