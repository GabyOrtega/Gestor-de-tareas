package Exceptions;

public class TareaNoEncontradaException extends TareaException {
    public TareaNoEncontradaException(String id) {
        super("No existe tarea con ID: " + id);
    }
}
