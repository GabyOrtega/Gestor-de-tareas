package Exceptions;

public class TareaDuplicadaException extends TareaException {
    public TareaDuplicadaException(String id) {
        super("Ya existe una tarea con ID: " + id);
    }
}
