package me.arycer.dam.dao;

import me.arycer.dam.exception.ClienteNoExistenteException;
import me.arycer.dam.exception.PedidoNoExistenteException;
import me.arycer.dam.model.Pedido;
import me.arycer.dam.util.Pair;

import java.util.List;

public interface PedidosDAO extends CrudDAO<Pedido> {
    /**
     * Devuelve los pedidos de un cliente por su id
     * @param id_cliente id del cliente
     * @return Lista de pedidos del cliente
     */
    List<Pedido> getPedidosByIdCliente(int id_cliente);

    /**
     * Devuelve los pedidos de un producto por su id
     * @param id_producto id del producto
     * @return Lista de pedidos del producto
     */
    List<Pedido> getPedidosByIdProducto(int id_producto);

    /**
     * Devuelve un pedido por el id del cliente y la fecha
     * @param id_cliente id del cliente
     * @param fecha fecha del pedido
     * @return Pedido
     * @throws PedidoNoExistenteException si el pedido no existe
     */
    Pedido getPedidoByClienteFecha(int id_cliente, String fecha) throws PedidoNoExistenteException;

    /**
     * Devuelve un pedido por su id
     * @param id_pedido id del pedido
     * @return Pedido
     * @throws PedidoNoExistenteException si el pedido no existe
     */
    Pedido getPedidoById(int id_pedido) throws PedidoNoExistenteException;

    /**
     * Crea un pedido de un cliente
     * @param id_cliente id del cliente
     * @param fecha fecha del pedido
     * @param productos_pedido lista de pares de id_producto y cantidad
     * @throws ClienteNoExistenteException si el cliente no existe
     */
    void crearPedidoCliente(int id_cliente, String fecha, List<Pair<Integer, Integer>> productos_pedido) throws ClienteNoExistenteException;
}
