package model.dao;

import java.util.List;

public interface IDAO<T> {
    void add(T obj);
    void delete(int id);
    T getOne(int id);
    List<T> getAll();
}
