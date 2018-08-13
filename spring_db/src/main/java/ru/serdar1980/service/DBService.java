package ru.serdar1980.service;

import ru.serdar1980.dao.BaseDao;

public abstract class DBService<T> implements IDBService<T> {
    protected BaseDao<T> dao;
}
