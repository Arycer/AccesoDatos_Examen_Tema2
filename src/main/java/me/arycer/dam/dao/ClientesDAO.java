package me.arycer.dam.dao;

import me.arycer.dam.exception.ClienteNoExistenteException;
import me.arycer.dam.model.Cliente;

public interface ClientesDAO extends CrudDAO<Cliente> {
    /**
     * Devuelve un cliente por su id
     * @param id_cliente id del cliente
     * @return Cliente
     * @throws ClienteNoExistenteException si el cliente no existe
     */
    Cliente getClienteById(int id_cliente) throws ClienteNoExistenteException;
}
