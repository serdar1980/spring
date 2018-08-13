package ru.serdar1980.service;

import java.util.List;

public interface IDBService<T> {
    void save(T t);

    void delete(T t);

    T findById(Long id);

    List<T> findAll();
}
