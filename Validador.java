import Exceptions.*;
import java.util.function.Predicate;

class Validador {

    public static final Predicate<String> NO_VACIO = 
        s -> s != null && !s.trim().isEmpty();
    
    public static final Predicate<String> ID_VALIDO = 
        s -> { if (!NO_VACIO.test(s)) return false;
             for (char c : s.toCharArray()) {
                if (!Character.isDigit(c)) return false;}
             return true;
             };

    public static final Predicate<String> DESCRIPCION_VALIDA = 
        s -> NO_VACIO.test(s) && s.length() >= 3 && s.length() <= 200;

    public static void validarId(String id) throws IdInvalidoException {
        if (!NO_VACIO.test(id)) {
            throw new IdInvalidoException("El ID no puede estar vacío.");
        }
        if (!ID_VALIDO.test(id)) {
            throw new IdInvalidoException(
                "El ID solo puede contener números."
            );
        }
    }

    public static void validarDescripcion(String desc) throws DescripcionInvalidaException {
        if (!NO_VACIO.test(desc)) {
            throw new DescripcionInvalidaException("La descripción no puede estar vacía.");
        }
        if (desc.length() < 3) {
            throw new DescripcionInvalidaException(
                "La descripción debe tener al menos 3 caracteres."
            );
        }
        if (desc.length() > 100) {
            throw new DescripcionInvalidaException(
                "La descripción no puede superar los 100 caracteres."
            );
        }
    }
}