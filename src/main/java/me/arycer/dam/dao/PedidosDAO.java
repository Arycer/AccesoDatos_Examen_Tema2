package me.arycer.dam.dao;

import me.arycer.dam.exception.ClienteNoExistenteException;
import me.arycer.dam.exception.PedidoNoExistenteException;
import me.arycer.dam.model.Pedido;
import me.arycer.dam.util.Pair;

import java.util.List;

public interface PedidosDAO extends CrudDAO<Pedido> {
    List<Pedido> getPedidosByIdCliente(int id_cliente);

    List<Pedido> getPedidosByIdProducto(int id_producto);

    Pedido getPedidoByClienteFecha(int id_cliente, String fecha) throws PedidoNoExistenteException;

    Pedido getPedidoById(int id_pedido) throws PedidoNoExistenteException;

    void crearPedidoCliente(int id_cliente, String fecha, List<Pair<Integer, Integer>> productos_pedido) throws ClienteNoExistenteException;
}
