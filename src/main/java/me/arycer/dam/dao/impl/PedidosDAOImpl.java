package me.arycer.dam.dao.impl;

import me.arycer.dam.dao.DetallesPedidoDAO;
import me.arycer.dam.dao.PedidosDAO;
import me.arycer.dam.dao.ProductosDAO;
import me.arycer.dam.database.DBConnection;
import me.arycer.dam.exception.ClienteNoExistenteException;
import me.arycer.dam.exception.PedidoNoExistenteException;
import me.arycer.dam.model.Cliente;
import me.arycer.dam.model.Pedido;
import me.arycer.dam.model.Producto;
import me.arycer.dam.util.Pair;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PedidosDAOImpl implements PedidosDAO {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Connection connection;

    public PedidosDAOImpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void create(Pedido dato) {
        final String SQL = """
                INSERT INTO pedidos (fecha, id_cliente)
                VALUES (?, ?)
                """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            st.setDate(1, Date.valueOf(LocalDate.parse(dato.getFecha(), TIME_FORMATTER)));
            st.setInt(2, dato.getId_cliente());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pedido> read() {
        final List<Pedido> LISTA = new ArrayList<>();
        final String SQL = """
                SELECT * FROM pedidos
                """;

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {
                LISTA.add(new Pedido(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LISTA;
    }

    @Override
    public void update(Pedido dato) {
        final String SQL = """
            UPDATE pedidos SET fecha = ?, id_cliente = ?
            WHERE id = ?
            """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            st.setDate(1, Date.valueOf(LocalDate.parse(dato.getFecha(), TIME_FORMATTER)));
            st.setInt(2, dato.getId_cliente());
            st.setInt(3, dato.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        DetallesPedidoDAO detallesPedidoDAO = new DetallesPedidoDAOImpl();
        detallesPedidoDAO.deleteByIdPedido(id);

        String sql = """
            DELETE FROM pedidos
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
    public List<Pedido> getPedidosByIdCliente(int id_cliente) {
        final List<Pedido> LISTA = new ArrayList<>();
        final String SQL = """
                SELECT * FROM pedidos
                WHERE id_cliente = ?
                """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            st.setInt(1, id_cliente);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LISTA.add(new Pedido(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LISTA;
    }

    @Override
    public List<Pedido> getPedidosByIdProducto(int id_producto) {
        final List<Pedido> LISTA = new ArrayList<>();
        final String SQL = """
                SELECT p.* FROM pedidos p, detalles_pedido rel
                WHERE p.id = rel.id_pedido
                  AND rel.id_producto = ?
                """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            st.setInt(1, id_producto);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LISTA.add(new Pedido(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return LISTA;
    }

    @Override
    public Pedido getPedidoByClienteFecha(int id_cliente, String fecha) throws PedidoNoExistenteException {
        Pedido pedido = null;
        String sql = """
            SELECT * FROM pedidos
            WHERE id_cliente = ?
              AND fecha = ?
            LIMIT 1
            """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id_cliente);
            st.setDate(2, Date.valueOf(LocalDate.parse(fecha, TIME_FORMATTER)));

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                pedido = new Pedido(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (pedido == null) {
            throw new PedidoNoExistenteException();
        }

        return pedido;
    }

    @Override
    public Pedido getPedidoById(int id_pedido) throws PedidoNoExistenteException {
        Pedido pedido = null;
        String sql = """
            SELECT * FROM pedidos
            WHERE id = ?
            LIMIT 1
            """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id_pedido);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                pedido = new Pedido(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (pedido == null) {
            throw new PedidoNoExistenteException();
        }

        return pedido;
    }

    @Override
    public void crearPedidoCliente(int id_cliente, String fecha, List<Pair<Integer, Integer>> productos_pedido) {
        ProductosDAO productosDAO = new ProductosDAOImpl();
        Pedido pedido;

        try {
            pedido = this.getPedidoByClienteFecha(id_cliente, fecha);
        } catch (PedidoNoExistenteException e) {
            this.create(new Pedido(fecha, id_cliente));
            pedido = this.getPedidoByClienteFecha(id_cliente, fecha);
        }

        final String SQL = """
                INSERT INTO detalles_pedido (id_pedido, id_producto, cantidad)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement st = connection.prepareStatement(SQL)) {
            connection.setAutoCommit(false);

            // ID Producto, Cantidad
            for (Pair<Integer, Integer> producto_cantidad : productos_pedido) {
                Producto producto = productosDAO.getProductoById(producto_cantidad.getLeft());

                st.setInt(1, pedido.getId());
                st.setInt(2, producto.getId());
                st.setInt(3, producto_cantidad.getRight());

                st.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
