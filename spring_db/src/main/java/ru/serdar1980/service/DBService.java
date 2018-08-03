package ru.serdar1980.service;

import ru.serdar1980.dao.BaseDao;
import ru.serdar1980.enumeration.DaoType;

public abstract class DBService<T> implements IDBService<T> {
    protected BaseDao<T> dao;

    public abstract void changeDBAcsses(DaoType type);
}
