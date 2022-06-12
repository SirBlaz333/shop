package com.my.container;

public interface Container {
    void put(Object key, Object value);
    Object get(Object key);
}
