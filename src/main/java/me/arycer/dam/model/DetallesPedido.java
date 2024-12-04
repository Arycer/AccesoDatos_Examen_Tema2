package me.arycer.dam.model;

public class DetallesPedido {
    private int id;
    private int id_pedido;
    private int id_producto;
    private int cantidad;

    public DetallesPedido(int id, int id_pedido, int id_producto, int cantidad) {
        this(id_pedido, id_producto, cantidad);
        this.id = id;
    }

    public DetallesPedido(int id_pedido, int id_producto, int cantidad) {
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public int getId_producto() {
        return id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return "DetallesPedido{" +
                "id=" + id +
                ", id_pedido=" + id_pedido +
                ", id_producto=" + id_producto +
                ", cantidad=" + cantidad +
                '}';
    }
}
