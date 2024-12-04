package me.arycer.dam.dao;

import me.arycer.dam.model.DetallesPedido;

import java.util.List;

public interface DetallesPedidoDAO extends CrudDAO<DetallesPedido> {
    List<DetallesPedido> getDetallesByIdPedido(int id_pedido);

    void deleteByIdPedido(int id_pedido);
}
