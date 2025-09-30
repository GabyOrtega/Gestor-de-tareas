package Exceptions;

public class DescripcionInvalidaException extends TareaException {
    public DescripcionInvalidaException(String motivo) {
        super("Descripción inválida: " + motivo);
    }
}
