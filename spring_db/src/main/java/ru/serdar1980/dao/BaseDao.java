package ru.serdar1980.dao;

import java.util.List;

public interface BaseDao<T> {
    int save(T t);

    int delete(T t);

    T findById(Long id);

    List<T> findAll();

}
