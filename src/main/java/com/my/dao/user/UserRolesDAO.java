package com.my.dao.user;

import com.my.dao.DAO;

public interface UserRolesDAO extends DAO {
    String getUserRoleById(int id);

    int getUserRoleId(String userRole);
}
