import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.List;
import Exceptions.TareaException;

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
        } while (opcion != 7);
    }

    private void showOptions() {
        System.out.println("Por favor seleccione una de las siguientes opciones:"); 
        System.out.println("1 - Agregar tarea");
        System.out.println("2 - Eliminar tarea");
        System.out.println("3 - Completar tarea");
        System.out.println("4 - Mostrar lista de tareas ordenadas por fecha");
        System.out.println("5 - Filtrar tareas");
        System.out.println("6 - Exportar tareas a CSV");
        System.out.println("7 - Salir");
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
                menuFilterTareas();
                break;
            case 6:
                menuExportToCSV();
                break;
            case 7:
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
            String description = scanner.nextLine().trim();

            System.out.print("Ingrese fecha de vencimiento (DD/MM/YYYY) [Enter para 1 semana]: ");
            String dateStr = scanner.nextLine().trim();

            LocalDate date;
            if (dateStr.isEmpty()) {
                date = LocalDate.now().plusWeeks(1);
            } else {
                date = Tarea.parseDate(dateStr);
            }
            
            gestorTareas.addTarea(id, description,date);
            System.out.println("Tarea agregada exitosamente.\n");
        
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha invalido. Use DD/MM/YYYY\n");           
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
        
        System.out.println("LISTA DE TAREAS ORDENADAS POR FECHA DE VENCIMIENTO\n");
        
        List<Tarea> tareas = gestorTareas.getAllTareas();
        tareas.forEach(System.out::println);
        
        System.out.println("Total: " + gestorTareas.numberOfTareas() + " tareas\n");
    }

    private void menuFilterTareas() {
        System.out.println("\n¿Qué tareas desea ver?");
        System.out.println("1 - Tareas completadas");
        System.out.println("2 - Tareas pendientes");
        System.out.println("3 - Tareas vencidas");
        System.out.print("Opcion: ");
        
        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            List<Tarea> tareasFiltered;
            String title;
            
            switch (opcion) {
                case 1:
                    tareasFiltered = gestorTareas.getTareasCompleted();
                    title = "TAREAS COMPLETADAS";
                    break;
                case 2:
                    tareasFiltered = gestorTareas.getTareasPending();
                    title = "TAREAS PENDIENTES";
                    break;
                case 3:
                    tareasFiltered = gestorTareas.getTareasExpired();
                    title = "TAREAS VENCIDAS";
                    break;
                default:
                    System.out.println("Opción inválida\n");
                    return;
            }
            
            if (tareasFiltered.isEmpty()) {
                System.out.println("No hay tareas en esta categoria.\n");
                return;
            }
            
            System.out.println(title);
            tareasFiltered.forEach(System.out::println);
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un numero válido\n");
        }
    }

    private void menuExportToCSV() {
        try {
            System.out.print("Ingrese el nombre del archivo CSV (sin extension): ");
            String nameFile = scanner.nextLine().trim();
            
            if (nameFile.isEmpty()) {
                nameFile = "tareas";
            }
            
            gestorTareas.exportToCSV(nameFile + ".csv");
            System.out.println("Exportación completada exitosamente.\n");
            
        } catch (IOException e) {
            System.out.println("Error al exportar: " + e.getMessage() + "\n");
        }
    }
}