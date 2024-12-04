package me.arycer.dam.dao;

import me.arycer.dam.exception.ProductoNoExistenteException;
import me.arycer.dam.model.Producto;

public interface ProductosDAO extends CrudDAO<Producto> {
    Producto getProductoById(int id_producto) throws ProductoNoExistenteException;
}
