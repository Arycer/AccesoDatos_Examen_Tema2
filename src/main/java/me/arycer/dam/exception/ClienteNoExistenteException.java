package me.arycer.dam.exception;

public class ClienteNoExistenteException extends RuntimeException {
    public ClienteNoExistenteException() {
        super("El cliente seleccionado no existe");
    }
}
