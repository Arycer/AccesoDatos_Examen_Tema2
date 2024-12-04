package me.arycer.dam.model;

public class Pedido {
    private int id;
    private String fecha;
    private int id_cliente;

    public Pedido(String fecha, int id_cliente) {
        this.fecha = fecha;
        this.id_cliente = id_cliente;
    }

    public Pedido(int id, String fecha, int id_cliente) {
        this(fecha, id_cliente);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", id_cliente=" + id_cliente +
                '}';
    }
}
