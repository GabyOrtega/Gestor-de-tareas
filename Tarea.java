import Exceptions.TareaException;

public class Tarea{

    private String id;
    private String description;
    private boolean completed;

    public Tarea(String id, String description){
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    public String getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean isCompleted(){
        return this.completed;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public String toJSON() {
        return String.format(
            "{\"id\":\"%s\",\"description\":\"%s\",\"completed\":%b}",
            id.replace("\"", "\\\""),
            description.replace("\"", "\\\""),
            completed
        );
    }
    
    public static Tarea fromJSON(String json) throws TareaException {
        try {
            String id = extractValue(json, "id");
            String description = extractValue(json, "description");
            boolean completed = Boolean.parseBoolean(extractValue(json, "completed"));
            
            Tarea tarea = new Tarea(id, description);
            tarea.setCompleted(completed);
            return tarea;
        } catch (Exception e) {
            throw new TareaException("Error al parsear JSON: " + e.getMessage());
        }
    }

    private static String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern) + pattern.length();
        
        if (json.charAt(start) == '"') {
            start++;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } else {
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            return json.substring(start, end).trim();
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Descripcion: %s | Completada: %s", 
            id, description, completed ? "Si" : "No");
    }
}
