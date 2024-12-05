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
     * Actualiza un dato
     * @param dato Dato a actualizar
     */
    void update(T dato);

    /**
     * Elimina un dato
     * @param id Id del dato a eliminar
     */
    void delete(int id);
}
