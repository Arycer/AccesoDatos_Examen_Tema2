package me.arycer.dam.dao;

import java.util.List;

/**
 * Interfaz que define las operaciones CRUD
 * @param <T> Tipo de dato
 */
public interface CrudDAO<T> {
    /**
     * Crea un dato
     * @param dato Dato a crear
     */
    void create(T dato);

    /**
     * Lee todos los datos
     * @return Lista de datos
     */
    List<T> read();

    /**
     * Lee un dato por su id
     * @param dato Dato a leer
     */
    void update(T dato);

    /**
     * Actualiza un dato
     * @param id Id del dato a actualizar
     */
    void delete(int id);
}
