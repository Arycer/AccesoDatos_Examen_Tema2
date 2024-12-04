package me.arycer.dam.exception;

public class PedidoNoExistenteException extends RuntimeException {
    public PedidoNoExistenteException() {
        super("El pedido seleccionado no existe");
    }
}
