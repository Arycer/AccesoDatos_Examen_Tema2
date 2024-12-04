package me.arycer.dam.exception;

public class ProductoNoExistenteException extends RuntimeException {
    public ProductoNoExistenteException() {
        super("El producto seleccionado no existe");
    }
}
