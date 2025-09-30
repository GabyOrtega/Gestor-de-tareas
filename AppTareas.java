import java.util.HashMap;
import java.util.Scanner;

public class AppTareas {
    HashMap<String,Tarea> tareas = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AppTareas app = new AppTareas();
        app.showInteractiveMenu();
    }

    public void addTarea(){
        System.out.println("Ingrese el ID de la tarea: ");
        String id = scanner.nextLine();
        if (tareas.containsKey(id)) {
            System.out.println("La tarea con ID " + id + " ya existe.");
        }
        else{
            System.out.println("Ingrese la descripción de la tarea:");
            String description = scanner.nextLine();
            Tarea tarea = new Tarea(id, description);
            tareas.put(id, tarea);
            System.out.println("La tarea fue agregada.");
        }
    }

    public void deleteTarea(){
        System.out.println("Ingrese el ID de la tarea:");
        String id = scanner.nextLine();
        if (tareas.containsKey(id)) { 
            tareas.remove(id);
            System.out.println("La tarea fue eliminada.");
        }
        else{
            System.out.println("No existe tarea con ese ID.");
        }
    }

    public void completeTarea(){
        System.out.println("Ingrese el ID de la tarea a completar:");
        String id = scanner.nextLine();
        if (tareas.containsKey(id)) { 
            Tarea tarea = tareas.get(id); 
            tarea.setCompleted(true);
            System.out.println("La tarea ha sido completada.");
        }
        else{
            System.out.println("No existe tarea con ese ID.");
        }
    }

    public void showTareas(){
        if (tareas.isEmpty()){
            System.out.println("No hay tareas en la lista para mostrar.");
        }
        else{
        tareas.forEach((id, tarea) -> {
            System.out.println("ID: "+ id + ", Descripción: " + tarea.getDescription() + ", Completada: " + tarea.isCompleted());
            });
        }
    }

    public void showInteractiveMenu(){
        
        int opcion = 0;
        System.out.println("Bienvenido al administrador de tareas personales.");

        do{
            System.out.println("Por favor seleccione una de las siguientes opciones:"); 
            System.out.println("1 - Agregar tarea");
            System.out.println("2 - Eliminar tarea");
            System.out.println("3 - Completar tarea");
            System.out.println("4 - Mostrar lista de tareas");
            System.out.println("5 - Salir");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion){
                case 1:
                    addTarea();
                    break;
                case 2:
                    deleteTarea();
                    break;
                case 3:
                    completeTarea();
                    break;
                case 4:
                    showTareas();
                    break;
                case 5:
                    System.out.println("Salida exitosa. Hasta luego!");
                    break;    
                default:
                    System.out.println("Se ingreso una opción incorrecta. Por favor intente nuevamente:");
                    break;
            }        
        } while (opcion != 5);
        scanner.close();
    }
}