import java.util.Scanner;

import Exceptions.TareaException;

import java.util.List;

public class MenuManager {
    private Scanner scanner;
    private GestorTareas gestorTareas;

    public MenuManager() {
        this.scanner = new Scanner(System.in);
        this.gestorTareas = new GestorTareas();
    }

    public void start() {
        showWelcome();
        showMainMenu();
        scanner.close();
    }

    private void showWelcome() {
        System.out.println("Bienvenido al administrador de tareas personales.");
    }

    private void showMainMenu() {
        int opcion = 0;

        do {
            try {
                showOptions();
                opcion = readOption();
                processOption(opcion);
                
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero valido\n");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage() + "\n");
            }
        } while (opcion != 5);
    }

    private void showOptions() {
        System.out.println("Por favor seleccione una de las siguientes opciones:"); 
        System.out.println("1 - Agregar tarea");
        System.out.println("2 - Eliminar tarea");
        System.out.println("3 - Completar tarea");
        System.out.println("4 - Mostrar lista de tareas");
        System.out.println("5 - Salir");
    }

    private int readOption() {
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private void processOption(int opcion) {
        switch (opcion) {
            case 1:
                menuAddTarea();
                break;
            case 2:
                menuDeleteTarea();
                break;
            case 3:
                menuCompleteTarea();
                break;
            case 4:
                menuShowTareas();
                break;
            case 5:
                System.out.println("\nSalida exitosa. Hasta luego!");
                scanner.close();
                break;
            default:
                System.out.println("Opcion incorrecta. Intente nuevamente.\n");
                break;
        }
    }

    private void menuAddTarea() {
        try {
            System.out.print("Ingrese el ID de la tarea: ");
            String id = scanner.nextLine().trim();
            
            System.out.print("Ingrese la descripcion de la tarea: ");
            String descripcion = scanner.nextLine().trim();
            
            gestorTareas.addTarea(id, descripcion);
            System.out.println("Tarea agregada exitosamente.\n");
            
        } catch (TareaException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void menuDeleteTarea() {
        try {
            System.out.print("Ingrese el ID de la tarea: ");
            String id = scanner.nextLine().trim();
            
            gestorTareas.deleteTarea(id);
            System.out.println("Tarea eliminada exitosamente.\n");
            
        } catch (TareaException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void menuCompleteTarea() {
        try {
            System.out.print("Ingrese el ID de la tarea a completar: ");
            String id = scanner.nextLine().trim();
            
            gestorTareas.completeTarea(id);
            System.out.println("Tarea completada exitosamente.\n");
            
        } catch (TareaException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void menuShowTareas() {
        if (gestorTareas.isEmpty()) {
            System.out.println("No hay tareas en la lista para mostrar.\n");
            return;
        }
        
        System.out.println("LISTA DE TAREAS\n");
        
        List<Tarea> tareas = gestorTareas.getAllTareas();
        tareas.forEach(System.out::println);
        
        System.out.println("Total: " + gestorTareas.numberOfTareas() + " tareas\n");
    }
}