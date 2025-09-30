package Exceptions;

public class IdInvalidoException extends TareaException {
    public IdInvalidoException(String motivo) {
        super("ID inválido: " + motivo);
    }
}