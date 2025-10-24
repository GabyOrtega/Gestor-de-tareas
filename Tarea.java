import Exceptions.TareaException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Tarea implements Comparable<Tarea>{

    private String id;
    private String description;
    private boolean completed;
    private LocalDate expirationDate;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Tarea(String id, String description, LocalDate expirationDate){
        this.id = id;
        this.description = description;
        this.completed = false;
        this.expirationDate = expirationDate;
    }

    // Constructor sin fecha (fecha por defecto: 1 semana desde hoy)
    public Tarea(String id, String description) {
        this(id, description, LocalDate.now().plusWeeks(1));
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

    public boolean isExpired() {
        return !completed && expirationDate.isBefore(LocalDate.now());
    }

    public LocalDate getExpirationDate(){
        return this.expirationDate;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public void setExpirationDate(LocalDate date) {
        this.expirationDate = date;
    }

    public String toJSON() {
        return String.format(
            "{\"id\":\"%s\",\"description\":\"%s\",\"completed\":%b,\"expirationDate\":\"%s\"}",
            id.replace("\"", "\\\""),
            description.replace("\"", "\\\""),
            completed,
            expirationDate.toString()
        );
    }
    
    public static Tarea fromJSON(String json) throws TareaException {
        try {
            String id = extractValue(json, "id");
            String description = extractValue(json, "description");
            boolean completed = Boolean.parseBoolean(extractValue(json, "completed"));
            String dateStr = extractValue(json, "expirationDate");
            LocalDate date = LocalDate.parse(dateStr);
            
            Tarea tarea = new Tarea(id, description, date);
            tarea.setCompleted(completed);
            return tarea;
        } catch (Exception e) {
            throw new TareaException("Error al parsear JSON: " + e.getMessage());
        }
    }

    // Conversión a CSV
    public String toCSV() {
        String state;

        if (isExpired()) {
            state = "VENCIDA";
        } else if (completed) {
            state = "Completada";
        } else {
            state = "Pendiente";
        }
        
        return String.format("%s,%s,%s,%s",
            escapeCSV(id),
            escapeCSV(description),
            state,
            expirationDate.format(FORMATTER)
        );
    }

    private String escapeCSV(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
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

    // Ordenamiento cronológico por fecha de vencimiento
    @Override
    public int compareTo(Tarea other) {
        return this.expirationDate.compareTo(other.expirationDate);
    }

    @Override
    public String toString() {
        String state = completed ? "Completada" : 
                       (isExpired() ? "VENCIDA" : "Pendiente");
        return String.format("ID: %s | Descripcion: %s | Estado: %s | Vence: %s", 
            id, description, state, expirationDate.format(FORMATTER));
    }

    // Parser de fecha desde String
    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, FORMATTER);
    }
}
