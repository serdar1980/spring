package ru.serdar1980.dao;

import java.util.List;

public interface BaseDao<T> {
    int saveDao(T t);

    int deleteDao(T t);

    T findByIdDao(Long id);

    List<T> findAllDao();

}
