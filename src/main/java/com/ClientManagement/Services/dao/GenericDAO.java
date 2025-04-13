package com.ClientManagement.Services.dao;

import java.util.List;

public interface GenericDAO<T> {
    T findById(int id);
    void create(T entity);
    void update ();
    void delete(int id);
    List<T> findAll();
}
