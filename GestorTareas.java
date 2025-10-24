import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.io.IOException;
import java.time.LocalDate;

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

    public void addTarea(String id, String description, LocalDate expirationDate) throws TareaException {
        Validador.validarId(id);
        
        if (tareas.containsKey(id)) {
            throw new TareaDuplicadaException(id);
        }
        
        Validador.validarDescripcion(description);
        
        Tarea tarea = new Tarea(id, description, expirationDate);
        tareas.put(id, tarea);
        guardarTareas();
    }

    // Agregar tarea sin fecha (usa fecha por defecto)
    public void addTarea(String id, String description) throws TareaException{
        addTarea(id, description, LocalDate.now().plusWeeks(1));
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
        if (tarea.isExpired()) {
            throw new TareaException(
                "No se puede completar la tarea '" + id + 
                "' porque está vencida."
            );
        }

        tarea.setCompleted(true);
        guardarTareas();
    }    

    public List<Tarea> getAllTareas() {
        return tareas.values().stream()
            .sorted()
            .collect(Collectors.toList());
    }

     public List<Tarea> filterTareas(Predicate<Tarea> filter) {
        return tareas.values().stream()
            .filter(filter)
            .sorted()
            .collect(Collectors.toList());
    }

    public List<Tarea> getTareasCompleted() {
        return filterTareas(Tarea::isCompleted);
    }

    public List<Tarea> getTareasPending() {
        return filterTareas(t -> !t.isCompleted() && !t.isExpired());
    }

    public List<Tarea> getTareasExpired() {
        return filterTareas(Tarea::isExpired);
    }

    public void exportToCSV(String nameFile) throws IOException {
        List<Tarea> listTareas = getAllTareas();
        ExportadorCSV.exportTareas(listTareas, nameFile);
    }

    public boolean isEmpty() {
        return tareas.isEmpty();
    }

    public int numberOfTareas() {
        return tareas.size();
    }
}