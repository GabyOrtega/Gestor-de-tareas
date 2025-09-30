import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;
import Exceptions.*;

public class GestorTareas {
    private HashMap<String, Tarea> tareas;
    private static String ARCHIVO = "tareas.json";

    public GestorTareas() {
        this.tareas = new HashMap<>();
        cargarTareas();
    }

    private void cargarTareas() {
        try {
            this.tareas = ArchivosManager.loadFile(ARCHIVO);
        } catch (IOException e) {
            System.out.println("Advertencia: Error al cargar archivo - " + e.getMessage());
        } catch (TareaException e) {
            System.out.println("Advertencia: Error al parsear tareas - " + e.getMessage());
        }
    }

    private void guardarTareas() {
        try {
            ArchivosManager.saveFile(tareas, ARCHIVO);
        } catch (IOException e) {
            System.out.println("Advertencia: Error al guardar tareas - " + e.getMessage());
        }
    }

    public void addTarea(String id, String descripcion) throws TareaException {
        Validador.validarId(id);
        
        if (tareas.containsKey(id)) {
            throw new TareaDuplicadaException(id);
        }
        
        Validador.validarDescripcion(descripcion);
        
        Tarea tarea = new Tarea(id, descripcion);
        tareas.put(id, tarea);
        guardarTareas();
    }

    public void deleteTarea(String id) throws TareaException {
        if (!tareas.containsKey(id)) {
            throw new TareaNoEncontradaException(id);
        }
        
        tareas.remove(id);
        guardarTareas();
    }

    public void completeTarea(String id) throws TareaException {
        if (!tareas.containsKey(id)) {
            throw new TareaNoEncontradaException(id);
        }
        
        Tarea tarea = tareas.get(id);
        tarea.setCompleted(true);
        guardarTareas();
    }

    public List<Tarea> getAllTareas() {
        return tareas.values().stream()
            .collect(Collectors.toList());
    }

    public boolean isEmpty() {
        return tareas.isEmpty();
    }

    public int numberOfTareas() {
        return tareas.size();
    }
}