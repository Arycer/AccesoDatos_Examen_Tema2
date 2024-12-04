package me.arycer.dam.dao;

import me.arycer.dam.exception.ProductoNoExistenteException;
import me.arycer.dam.model.Producto;

public interface ProductosDAO extends CrudDAO<Producto> {
    /**
     * Devuelve un producto por su id
     * @param id_producto id del producto
     * @return Producto
     * @throws ProductoNoExistenteException si el producto no existe
     */
    Producto getProductoById(int id_producto) throws ProductoNoExistenteException;
}
