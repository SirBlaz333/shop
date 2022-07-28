package com.my.dao.manufacturer;

import com.my.dao.DAO;

public interface ManufacturerDAO extends DAO {
    String getManufacturerById(int id);
    int getManufacturerId(String name);
}
