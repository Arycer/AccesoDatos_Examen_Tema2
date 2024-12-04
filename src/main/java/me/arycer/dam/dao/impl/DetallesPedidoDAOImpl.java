package me.arycer.dam.dao.impl;

import me.arycer.dam.dao.DetallesPedidoDAO;
import me.arycer.dam.database.DBConnection;
import me.arycer.dam.model.DetallesPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallesPedidoDAOImpl implements DetallesPedidoDAO {
    private final Connection connection;

    public DetallesPedidoDAOImpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void create(DetallesPedido dato) {
        final String SQL = """
                INSERT INTO detalles_pedido (id_pedido, id_producto, cantidad)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            st.setInt(1, dato.getId_pedido());
            st.setInt(2, dato.getId_producto());
            st.setInt(3, dato.getCantidad());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DetallesPedido> read() {
        final List<DetallesPedido> LISTA = new ArrayList<>();
        final String SQL = """
                SELECT * FROM detalles_pedido
                """;

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {
                LISTA.add(new DetallesPedido(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LISTA;
    }

    @Override
    public void update(DetallesPedido dato) {
        final String SQL = """
            UPDATE detalles_pedido SET id_pedido = ?, id_producto = ?, cantidad = ?
            WHERE id = ?
            """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            st.setInt(1, dato.getId_pedido());
            st.setInt(2, dato.getId_producto());
            st.setInt(3, dato.getCantidad());
            st.setInt(4, dato.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = """
            DELETE FROM detalles_pedido
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
    public List<DetallesPedido> getDetallesByIdPedido(int id_pedido) {
        final List<DetallesPedido> LISTA = new ArrayList<>();
        final String SQL = """
                SELECT *
                FROM detalles_pedido
                WHERE id_pedido = ?
                """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            st.setInt(1, id_pedido);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LISTA.add(new DetallesPedido(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LISTA;
    }

    @Override
    public void deleteByIdPedido(int id_pedido) {
        String sql = """
            DELETE FROM detalles_pedido
            WHERE id_pedido = ?
            """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id_pedido);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
