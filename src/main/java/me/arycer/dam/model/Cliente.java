package me.arycer.dam.model;

public class Cliente {
    private int id;
    private String nombre;
    private String correo;

    public Cliente(int id, String nombre, String correo) {
        this(nombre, correo);
        this.id = id;
    }

    public Cliente(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
