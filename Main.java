import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        Scanner scanner = new Scanner(System.in);

        // Validación del DNI
        String dni;
        while (true) {
            System.out.println("Introduce tu DNI (8 números y 1 letra):");
            dni = scanner.nextLine();

            // Validar el formato del DNI (8 números y 1 letra)
            if (dni.matches("^[0-9]{8}[A-Za-z]$")) {
                break; // Salir del ciclo si el DNI es válido
            } else {
                System.out.println("DNI inválido. Debe tener 8 números seguidos de una letra.");
            }
        }

        // Si el DNI es válido, insertamos el usuario en la base de datos
        if (conexion.insertarUsuario(dni)) {
            System.out.println("Usuario registrado correctamente.");
        }

        Gastos gestionGastos = new Gastos(conexion, dni);

        while (true) {
            System.out.println("""
                ¿Qué deseas realizar?
                1. Registrar Gasto
                2. Registrar Ingreso
                3. Mostrar Movimientos
                4. Salir
            """);
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> {
                    // Lista de conceptos válidos para el gasto
                    String[] conceptosValidosGasto = {"Vacaciones", "Alquiler", "IRPF", "Vicios"};
                    String concepto = "";
                    boolean conceptoValido = false;

                    // Repetir hasta que el usuario ingrese un concepto válido
                    while (!conceptoValido) {
                        System.out.println("Conceptos disponibles: Vacaciones, Alquiler, IRPF, Vicios.");
                        System.out.println("Introduce el concepto del gasto:");
                        concepto = scanner.nextLine();

                        // Verificar si el concepto ingresado es válido
                        for (String c : conceptosValidosGasto) {
                            if (c.equalsIgnoreCase(concepto)) {
                                conceptoValido = true;
                                break;
                            }
                        }

                        if (!conceptoValido) {
                            System.out.println("Concepto no válido. Intenta nuevamente.");
                        }
                    }

                    System.out.println("Introduce la cantidad:");
                    double cantidad = scanner.nextDouble();
                    gestionGastos.registrarGasto(concepto, cantidad);
                }
                case 2 -> {
                    // Lista de conceptos válidos para el ingreso
                    String[] conceptosValidosIngreso = {"Nómina", "Venta"};
                    String concepto = "";
                    boolean conceptoValido = false;

                    // Repetir hasta que el usuario ingrese un concepto válido
                    while (!conceptoValido) {
                        System.out.println("Conceptos disponibles: Nómina, Venta.");
                        System.out.println("Introduce el concepto del ingreso:");
                        concepto = scanner.nextLine();

                        // Verificar si el concepto ingresado es válido
                        for (String c : conceptosValidosIngreso) {
                            if (c.equalsIgnoreCase(concepto)) {
                                conceptoValido = true;
                                break;
                            }
                        }

                        if (!conceptoValido) {
                            System.out.println("Concepto no válido. Intenta nuevamente.");
                        }
                    }

                    System.out.println("Introduce la cantidad:");
                    double cantidad = scanner.nextDouble();
                    gestionGastos.registrarIngreso(concepto, cantidad);
                }
                case 3 -> gestionGastos.mostrarMovimientos();
                case 4 -> {
                    System.out.println("¡Hasta luego!");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }
}
