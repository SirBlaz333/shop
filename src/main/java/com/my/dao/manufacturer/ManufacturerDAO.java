package com.my.dao.manufacturer;

import com.my.dao.DAO;

import java.util.List;

public interface ManufacturerDAO extends DAO {
    String getManufacturerById(int id);
    int getManufacturerId(String name);
}
