package me.arycer.dam.dao.impl;

import me.arycer.dam.dao.ClientesDAO;
import me.arycer.dam.database.DBConnection;
import me.arycer.dam.exception.ClienteNoExistenteException;
import me.arycer.dam.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAOImpl implements ClientesDAO {
    private final Connection connection;

    public ClientesDAOImpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void create(Cliente dato) {
        final String SQL = """
                INSERT INTO clientes (nombre, correo)
                VALUES (?, ?)
                """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            st.setString(1, dato.getNombre());
            st.setString(2, dato.getCorreo());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cliente> read() {
        final List<Cliente> LISTA = new ArrayList<>();
        final String SQL = """
                SELECT * FROM clientes
                """;

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {
                LISTA.add(new Cliente(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LISTA;
    }

    @Override
    public void update(Cliente dato) {
        final String SQL = """
            UPDATE clientes SET nombre = ?, correo = ?
            WHERE id = ?
            """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            st.setString(1, dato.getNombre());
            st.setString(2, dato.getCorreo());
            st.setInt(3, dato.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = """
            DELETE FROM clientes
            WHERE id = ?
            """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cliente getClienteById(int id_cliente) throws ClienteNoExistenteException {
        Cliente cliente = null;
        String sql = """
            SELECT * FROM clientes
            WHERE id = ?
            LIMIT 1
            """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id_cliente);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (cliente == null) {
            throw new ClienteNoExistenteException();
        }

        return cliente;
    }
}
