
// Importamos las clases necesarias
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


// Clase Contacto: Representa un contacto en la agenda. Es inmutable para mayor seguridad.
final class Contacto {
    private final String nombre;
    private final String telefono;

    // Constructor que inicializa los valores del contacto
    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Métodos getters
    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    // Representación en cadena del contacto
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Teléfono: " + telefono;
    }
}

//Clase InterfazUsuario: Maneja la entrada y salida del usuario.
class InterfazUsuario {
    private final Scanner scanner;

    public InterfazUsuario(Scanner scanner) {
        this.scanner = scanner;
    }

    // Muestra el menú principal
    public void mostrarMenu() {
        System.out.println("\n========== Menú ==========");
        System.out.println("1. Agregar contacto");
        System.out.println("2. Mostrar contactos");
        System.out.println("3. Eliminar contacto");
        System.out.println("4. Salir");
        System.out.println("=============================");
        System.out.print("Seleccione una opción: ");
    }

    // Obtiene una entrada numérica con manejo de errores
    public int obtenerEntradaNumerica() {
        while (true) {
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpia el buffer
                return opcion;
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
                scanner.nextLine(); // Limpia el buffer
            }
        }
    }

    // Obtiene una entrada de texto con un mensaje personalizado
    public String obtenerEntradaTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}

//Clase Agenda: Contiene la lógica de negocios para gestionar los contactos.
public class Agenda {
    private final ArrayList<Contacto> contactos; // Lista de contactos
    private final InterfazUsuario ui; // Manejo de interfaz de usuario

    // Constructor de la agenda
    public Agenda(Scanner scanner) {
        this.contactos = new ArrayList<>();
        this.ui = new InterfazUsuario(scanner);
    }

    // Agrega un contacto a la lista
    public void agregarContacto(String nombre, String telefono) {
        // Validaciones
        if (nombre.isEmpty() || telefono.isEmpty()) {
            System.out.println("Error: El nombre y el teléfono no pueden estar vacíos.");
            return;
        }
        if (!telefono.matches("\\d+")) {
            System.out.println("Error: El teléfono debe contener solo números.");
            return;
        }

        // Verifica si ya existe el contacto
        boolean existe = contactos.stream()
            .anyMatch(c -> c.getNombre().equalsIgnoreCase(nombre) && c.getTelefono().equals(telefono));
        if (existe) {
            System.out.println("Error: Ya existe un contacto con este nombre y teléfono.");
            return;
        }

        // Agrega el contacto
        Contacto nuevoContacto = new Contacto(nombre, telefono);
        contactos.add(nuevoContacto);
        System.out.println("Contacto agregado: " + nuevoContacto);
    }

    // Muestra todos los contactos
    public void mostrarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("No hay contactos en la agenda.");
        } else {
            System.out.println("Lista de contactos:");
            contactos.forEach(System.out::println);
        }
    }

    // Elimina un contacto por su nombre
    public void eliminarContacto(String nombre) {
        // Busca el contacto usando streams
        Contacto contacto = contactos.stream()
            .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
            .findFirst()
            .orElse(null);

        // Si se encuentra, se elimina; si no, muestra un error
        if (contacto != null) {
            contactos.remove(contacto);
            System.out.println("Contacto eliminado: " + contacto);
        } else {
            System.out.println("Error: Contacto no encontrado.");
        }
    }

    // Lógica principal del programa
    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            ui.mostrarMenu(); // Muestra el menú principal
            int opcion = ui.obtenerEntradaNumerica(); // Obtiene la opción del usuario

            switch (opcion) {
                case 1 -> {
                    // Agregar un contacto
                    String nombre = ui.obtenerEntradaTexto("Ingrese el nombre del contacto: ");
                    String telefono = ui.obtenerEntradaTexto("Ingrese el teléfono del contacto: ");
                    agregarContacto(nombre, telefono);
                }
                case 2 -> mostrarContactos(); // Mostrar los contactos
                case 3 -> {
                    // Eliminar un contacto
                    String nombreEliminar = ui.obtenerEntradaTexto("Ingrese el nombre del contacto a eliminar: ");
                    eliminarContacto(nombreEliminar);
                }
                case 4 -> {
                    // Salir del programa
                    System.out.println("Saliendo del programa. Hasta luego!");
                    salir = true;
                }
                default -> System.out.println("Error: Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    // Método main: punto de entrada del programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Inicializa el Scanner manualmente
        Agenda agenda = new Agenda(scanner); // Inicializa la agenda con el Scanner
        agenda.ejecutar(); // Ejecuta el programa
        scanner.close(); // Cierra el Scanner manualmente
        
    }

}


