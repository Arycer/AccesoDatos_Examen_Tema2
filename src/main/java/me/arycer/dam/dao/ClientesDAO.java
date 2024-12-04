package me.arycer.dam.dao;

import me.arycer.dam.exception.ClienteNoExistenteException;
import me.arycer.dam.model.Cliente;

public interface ClientesDAO extends CrudDAO<Cliente> {
    Cliente getClienteById(int id_cliente) throws ClienteNoExistenteException;
}
