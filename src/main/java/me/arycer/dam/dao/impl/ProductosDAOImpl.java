package me.arycer.dam.dao.impl;

import me.arycer.dam.dao.ProductosDAO;
import me.arycer.dam.database.DBConnection;
import me.arycer.dam.exception.ProductoNoExistenteException;
import me.arycer.dam.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAOImpl implements ProductosDAO {
    private final Connection connection;

    public ProductosDAOImpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void create(Producto dato) {
        final String SQL = """
                INSERT INTO productos (nombre, precio)
                VALUES (?, ?)
                """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            connection.setAutoCommit(false);

            st.setString(1, dato.getNombre());
            st.setDouble(2, dato.getPrecio());

            st.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }

            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al hacer setAutoCommit(true): " + e.getMessage());
            }
        }
    }

    @Override
    public List<Producto> read() {
        final List<Producto> LISTA = new ArrayList<>();
        final String SQL = """
                SELECT * FROM productos
                """;

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {
                LISTA.add(new Producto(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LISTA;
    }

    @Override
    public void update(Producto dato) {
        final String SQL = """
            UPDATE productos SET nombre = ?, precio = ?
            WHERE id = ?
            """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            st.setString(1, dato.getNombre());
            st.setDouble(2, dato.getPrecio());
            st.setInt(3, dato.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = """
            DELETE FROM productos
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
    public Producto getProductoById(int id_producto) throws ProductoNoExistenteException {
        Producto producto = null;
        String sql = """
            SELECT * FROM productos
            WHERE id = ?
            LIMIT 1
            """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id_producto);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                producto = new Producto(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (producto == null) {
            throw new ProductoNoExistenteException();
        }

        return producto;
    }
}
