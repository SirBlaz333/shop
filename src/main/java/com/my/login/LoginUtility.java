package com.my.login;

import com.my.user.User;
import com.my.user.UserField;
import com.my.user.Users;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.my.user.UserField.*;

public class LoginUtility {
    private final Users users;

    public LoginUtility() {
        users = new Users();
    }

    public boolean userExists(String email){
        for(User user : users.getUserList()){
            if(user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public void setAttributes(HttpServletRequest request){
        List<UserField> parameters = new ArrayList<>();
        parameters.add(EMAIL);
        parameters.add(FIRSTNAME);
        parameters.add(LASTNAME);
        for(UserField userField : parameters){
            String parameter = userField.toString().toLowerCase();
            request.setAttribute(parameter, request.getParameter(parameter));
        }
    }
}
