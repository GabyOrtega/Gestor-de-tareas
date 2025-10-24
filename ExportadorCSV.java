import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorCSV {
    
    public static void exportTareas(List<Tarea> tareas, String nameFile) 
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameFile))) {
            // Encabezado CSV
            writer.write("ID,Descripcion,Estado,Fecha de Vencimiento\n");
            
            // Escribir cada tarea
            for (Tarea tarea : tareas) {
                writer.write(tarea.toCSV());
                writer.write("\n");
            }
            
            System.out.println("Tareas exportadas exitosamente a: " + nameFile);
        }
    }
}