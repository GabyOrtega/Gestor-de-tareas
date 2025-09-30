import java.io.*;
import java.util.HashMap;
import Exceptions.*;

public class ArchivosManager {
    private static String ARCHIVO_DEFAULT = "tareas.json";
    
    public static void saveFile(HashMap<String, Tarea> tareas, String archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write("[\n");
            
            int count = 0;
            int total = tareas.size();
            
             for (Tarea tarea : tareas.values()) {
                writer.write("  " + tarea.toJSON());
                if (count < total - 1) {
                    writer.write(",");
                }
                writer.write("\n");
                count++;
        }
            
            writer.write("]");
        }
    }

    public static HashMap<String, Tarea> loadFile(String archivo) throws IOException, TareaException {
        HashMap<String, Tarea> tareas = new HashMap<>();
        File file = new File(archivo);
        
        if (!file.exists()) {
            return tareas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            StringBuilder content = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                content.append(line.trim());
            }
            
            String json = content.toString();
            if (json.startsWith("[") && json.endsWith("]")) {
                json = json.substring(1, json.length() - 1);
                
                String[] tareasJSON = json.split("\\},\\{");
                for (String tareaJSON : tareasJSON) {
                    if (!tareaJSON.trim().isEmpty()) {
                        if (!tareaJSON.startsWith("{")) tareaJSON = "{" + tareaJSON;
                        if (!tareaJSON.endsWith("}")) tareaJSON = tareaJSON + "}";
                        
                        Tarea tarea = Tarea.fromJSON(tareaJSON);
                        tareas.put(tarea.getId(), tarea);
                    }
                }
            }
            
            System.out.println("Cargadas " + tareas.size() + " tareas desde: " + archivo);
        }
        
        return tareas;
    }

    public static void saveTareas(HashMap<String, Tarea> tareas) throws IOException {
        saveFile(tareas, ARCHIVO_DEFAULT);
    }

    public static HashMap<String, Tarea> loadTareas() throws IOException, TareaException {
        return loadFile(ARCHIVO_DEFAULT);
    }
}