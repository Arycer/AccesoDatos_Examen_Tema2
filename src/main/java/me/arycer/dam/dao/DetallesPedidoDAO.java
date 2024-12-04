package me.arycer.dam.dao;

import me.arycer.dam.model.DetallesPedido;

import java.util.List;

public interface DetallesPedidoDAO extends CrudDAO<DetallesPedido> {
    /**
     * Devuelve los detalles de un pedido por su id
     * @param id_pedido id del pedido
     * @return Lista de detalles del pedido
     */
    List<DetallesPedido> getDetallesByIdPedido(int id_pedido);

    /**
     * Elimina los detalles de un pedido por su id
     * @param id_pedido id del pedido
     */
    void deleteByIdPedido(int id_pedido);
}
