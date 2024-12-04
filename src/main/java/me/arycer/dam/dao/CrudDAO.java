package me.arycer.dam.dao;

import java.util.List;

public interface CrudDAO<T> {
    void create(T dato);

    List<T> read();

    void update(T dato);

    void delete(int id);
}
