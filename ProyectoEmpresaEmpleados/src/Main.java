import modelos.*; // Importa todas las clases del paquete modelos
import operaciones.*; // Importa todas las clases del paquete operaciones
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Empresa> todasLasEmpresas = new ArrayList<>();
    private static final List<Empleado> todosLosEmpleados = new ArrayList<>(); // Lista global de empleados

    // Atributos de las interfaces e instancias de las clases que las implementan
    private static final IOperacionEmpresa operacionEmpresa = new OperacionEmpresa();
    private static final IOperacionEmpleado operacionEmpleado = new OperacionEmpleado();

    public static void main(String[] args) {
        System.out.println("--- GESTIÓN DE EMPRESAS Y EMPLEADOS ---");

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = obtenerOpcionMenu();

            switch (opcion) {
                case 1: // Gestión de Empresas
                    menuEmpresas();
                    break;
                case 2: // Gestión de Empleados
                    menuEmpleados();
                    break;
                case 0:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
            // La pausa se maneja dentro de los sub-menús o al final del main si no hay sub-menús.
            // Aquí no la necesitamos si cada sub-menú ya la tiene.
            if (opcion != 0) {
                 System.out.println("\nPresione Enter para volver al menú principal...");
                 scanner.nextLine();
            }

        } while (opcion != 0);

        scanner.close();
    }

    // --- Métodos de Menú Principal y Soporte ---

    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestión de Empresas");
        System.out.println("2. Gestión de Empleados");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int obtenerOpcionMenu() {
        int opcion = -1;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            scanner.next(); // Limpiar el buffer
        }
        scanner.nextLine(); // Consumir el salto de línea pendiente
        return opcion;
    }

    // --- Menú de Gestión de Empresas ---

    private static void menuEmpresas() {
        int opcionEmpresa;
        do {
            System.out.println("\n--- MENÚ GESTIÓN DE EMPRESAS ---");
            System.out.println("1. Ingresar nueva empresa");
            System.out.println("2. Consultar todas las empresas");
            System.out.println("3. Buscar empresa por NIT");
            System.out.println("4. Asignar empleado a empresa");
            System.out.println("5. Actualizar empresa");
            System.out.println("6. Eliminar empresa");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcionEmpresa = obtenerOpcionMenu();

            switch (opcionEmpresa) {
                case 1: // Ingresar nueva empresa
                    ingresarEmpresa();
                    break;
                case 2: // Consultar todas las empresas
                    operacionEmpresa.listarTodasLasEmpresas(todasLasEmpresas);
                    break;
                case 3: // Buscar empresa por NIT
                    System.out.print("Ingrese el NIT de la empresa a buscar: ");
                    String nitBuscar = scanner.nextLine();
                    Empresa empresaEncontrada = operacionEmpresa.buscarEmpresaPorNit(nitBuscar, todasLasEmpresas);
                    if (empresaEncontrada != null) {
                        System.out.println("Empresa encontrada: " + empresaEncontrada.toString());
                    } else {
                        System.out.println("Empresa con NIT " + nitBuscar + " no encontrada.");
                    }
                    break;
                case 4: // Asignar empleado a empresa
                    asignarEmpleadoAEmpresa();
                    break;
                case 5: // Actualizar empresa
                    actualizarEmpresa();
                    break;
                case 6: // Eliminar empresa
                    eliminarEmpresa();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
            if (opcionEmpresa != 0) {
                System.out.println("\nPresione Enter para continuar en el menú de Empresas...");
                scanner.nextLine();
            }
        } while (opcionEmpresa != 0);
    }

    // --- Métodos para el Menú de Empresas ---

    private static void ingresarEmpresa() {
        System.out.println("\n--- INGRESAR NUEVA EMPRESA ---");
        System.out.print("Ingrese NIT de la empresa: ");
        String nit = scanner.nextLine();
        // Opcional: Validar si el NIT ya existe
        if (operacionEmpresa.buscarEmpresaPorNit(nit, todasLasEmpresas) != null) {
            System.out.println("Ya existe una empresa con este NIT. Por favor, intente con otro.");
            return;
        }

        System.out.print("Ingrese nombre de la empresa: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese dirección de la empresa: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese ciudad de la empresa: ");
        String ciudad = scanner.nextLine();

        // Podríamos permitir elegir el tipo de empresa (Empresa o EmpresaDesarrollo)
        System.out.print("¿Es una Empresa de Desarrollo? (s/n): ");
        String tipo = scanner.nextLine();
        Empresa nuevaEmpresa;
        if (tipo.equalsIgnoreCase("s")) {
            nuevaEmpresa = new EmpresaDesarrollo(nit, nombre, direccion, ciudad);
        } else {
            nuevaEmpresa = new Empresa(nit, nombre, direccion, ciudad);
        }

        operacionEmpresa.agregarEmpresa(nuevaEmpresa, todasLasEmpresas);
    }

    private static void asignarEmpleadoAEmpresa() {
        if (todosLosEmpleados.isEmpty()) {
            System.out.println("No hay empleados registrados para asignar.");
            return;
        }
        if (todasLasEmpresas.isEmpty()) {
            System.out.println("No hay empresas registradas para asignar empleados.");
            return;
        }

        System.out.println("\n--- ASIGNAR EMPLEADO A EMPRESA ---");
        System.out.print("Ingrese el documento del empleado a asignar: ");
        String documentoEmpleado = scanner.nextLine();
        Empleado empleado = operacionEmpleado.buscarEmpleadoPorDocumento(documentoEmpleado, todosLosEmpleados);

        if (empleado == null) {
            System.out.println("Empleado con documento " + documentoEmpleado + " no encontrado.");
            return;
        }

        System.out.print("Ingrese el NIT de la empresa a la que desea asignar el empleado: ");
        String nitEmpresa = scanner.nextLine();
        Empresa empresa = operacionEmpresa.buscarEmpresaPorNit(nitEmpresa, todasLasEmpresas);

        if (empresa == null) {
            System.out.println("Empresa con NIT " + nitEmpresa + " no encontrada.");
            return;
        }

        operacionEmpresa.asignarEmpleadoAEmpresa(empleado, empresa);
    }

    private static void actualizarEmpresa() {
        System.out.println("\n--- ACTUALIZAR EMPRESA ---");
        System.out.print("Ingrese el NIT de la empresa a actualizar: ");
        String nit = scanner.nextLine();
        Empresa empresaExistente = operacionEmpresa.buscarEmpresaPorNit(nit, todasLasEmpresas);

        if (empresaExistente == null) {
            System.out.println("Empresa con NIT " + nit + " no encontrada.");
            return;
        }

        System.out.println("Empresa actual: " + empresaExistente.toString());
        System.out.print("Ingrese nuevo nombre (dejar vacío para mantener): ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese nueva dirección (dejar vacío para mantener): ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese nueva ciudad (dejar vacío para mantener): ");
        String ciudad = scanner.nextLine();

        // Crear una "empresa actualizada" temporal para pasar al método
        Empresa empresaTemp = new Empresa(nit,
                                          nombre.isEmpty() ? empresaExistente.getNombre() : nombre,
                                          direccion.isEmpty() ? empresaExistente.getDireccion() : direccion,
                                          ciudad.isEmpty() ? empresaExistente.getCiudad() : ciudad);

        operacionEmpresa.actualizarEmpresa(nit, empresaTemp, todasLasEmpresas);
    }

    private static void eliminarEmpresa() {
        System.out.println("\n--- ELIMINAR EMPRESA ---");
        System.out.print("Ingrese el NIT de la empresa a eliminar: ");
        String nit = scanner.nextLine();
        operacionEmpresa.eliminarEmpresa(nit, todasLasEmpresas);
    }

    // --- Menú de Gestión de Empleados ---

    private static void menuEmpleados() {
        int opcionEmpleado;
        do {
            System.out.println("\n--- MENÚ GESTIÓN DE EMPLEADOS ---");
            System.out.println("1. Ingresar nuevo empleado");
            System.out.println("2. Consultar todos los empleados");
            System.out.println("3. Buscar empleado por documento");
            System.out.println("4. Calcular sueldo de empleado");
            System.out.println("5. Contar cantidad de empleados en una empresa");
            System.out.println("6. Actualizar empleado");
            System.out.println("7. Eliminar empleado");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcionEmpleado = obtenerOpcionMenu();

            switch (opcionEmpleado) {
                case 1: // Ingresar nuevo empleado
                    ingresarEmpleado();
                    break;
                case 2: // Consultar todos los empleados
                    operacionEmpleado.listarTodosLosEmpleados(todosLosEmpleados);
                    break;
                case 3: // Buscar empleado por documento
                    System.out.print("Ingrese el documento del empleado a buscar: ");
                    String docBuscar = scanner.nextLine();
                    Empleado empleadoEncontrado = operacionEmpleado.buscarEmpleadoPorDocumento(docBuscar, todosLosEmpleados);
                    if (empleadoEncontrado != null) {
                        System.out.println("Empleado encontrado: " + empleadoEncontrado.toString());
                    } else {
                        System.out.println("Empleado con documento " + docBuscar + " no encontrado.");
                    }
                    break;
                case 4: // Calcular sueldo de empleado
                    calcularSueldoDeEmpleado();
                    break;
                case 5: // Contar cantidad de empleados en una empresa
                    contarEmpleadosEnEmpresa();
                    break;
                case 6: // Actualizar empleado
                    actualizarEmpleado();
                    break;
                case 7: // Eliminar empleado
                    eliminarEmpleado();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
            if (opcionEmpleado != 0) {
                System.out.println("\nPresione Enter para continuar en el menú de Empleados...");
                scanner.nextLine();
            }
        } while (opcionEmpleado != 0);
    }

    // --- Métodos para el Menú de Empleados ---

    private static void ingresarEmpleado() {
        System.out.println("\n--- INGRESAR NUEVO EMPLEADO ---");
        System.out.print("Ingrese documento del empleado: ");
        String documento = scanner.nextLine();
        if (operacionEmpleado.buscarEmpleadoPorDocumento(documento, todosLosEmpleados) != null) {
            System.out.println("Ya existe un empleado con este documento. Por favor, intente con otro.");
            return;
        }

        System.out.print("Ingrese nombre del empleado: ");
        String nombre = scanner.nextLine();
        double sueldoHora = 0.0;
        int horasTrabajadas = 0;
        boolean entradaValida;

        do {
            entradaValida = true;
            try {
                System.out.print("Ingrese sueldo por hora: ");
                sueldoHora = scanner.nextDouble();
                System.out.print("Ingrese horas trabajadas: ");
                horasTrabajadas = scanner.nextInt();
                if (sueldoHora <= 0 || horasTrabajadas < 0) {
                    System.out.println("Sueldo por hora y horas trabajadas deben ser valores positivos.");
                    entradaValida = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número para sueldo y horas.");
                entradaValida = false;
                scanner.next(); // Limpiar el buffer
            }
        } while (!entradaValida);
        scanner.nextLine(); // Consumir el salto de línea pendiente

        // Elegir el tipo de empleado
        System.out.println("Seleccione el tipo de empleado:");
        System.out.println("1. Desarrollador");
        System.out.println("2. Gestor de Proyectos");
        System.out.println("3. Administrador");
        System.out.print("Opción: ");
        int tipoEmpleado = obtenerOpcionMenu();

        Empleado nuevoEmpleado = null;
        switch (tipoEmpleado) {
            case 1:
                nuevoEmpleado = new Desarrollador(documento, nombre, sueldoHora, horasTrabajadas);
                break;
            case 2:
                System.out.print("Ingrese el área del Gestor de Proyectos: ");
                String area = scanner.nextLine();
                nuevoEmpleado = new GestorProyectos(documento, nombre, sueldoHora, horasTrabajadas, area);
                break;
            case 3:
                nuevoEmpleado = new Admin(documento, nombre, sueldoHora, horasTrabajadas);
                break;
            default:
                System.out.println("Tipo de empleado no válido. Se creará como Desarrollador por defecto.");
                nuevoEmpleado = new Desarrollador(documento, nombre, sueldoHora, horasTrabajadas);
                break;
        }

        Empresa empresaAsignar = null;
        if (!todasLasEmpresas.isEmpty()) {
            System.out.print("¿Desea asignar este empleado a una empresa existente? (s/n): ");
            String asignar = scanner.nextLine();
            if (asignar.equalsIgnoreCase("s")) {
                System.out.print("Ingrese el NIT de la empresa a asignar: ");
                String nitEmpresa = scanner.nextLine();
                empresaAsignar = operacionEmpresa.buscarEmpresaPorNit(nitEmpresa, todasLasEmpresas);
                if (empresaAsignar == null) {
                    System.out.println("Empresa con NIT " + nitEmpresa + " no encontrada. El empleado no será asignado a una empresa.");
                }
            }
        } else {
            System.out.println("No hay empresas registradas para asignar empleados en este momento.");
        }
        operacionEmpleado.agregarEmpleado(nuevoEmpleado, empresaAsignar, todosLosEmpleados);
    }

    private static void calcularSueldoDeEmpleado() {
        System.out.println("\n--- CALCULAR SUELDO DE EMPLEADO ---");
        System.out.print("Ingrese el documento del empleado para calcular sueldo: ");
        String documento = scanner.nextLine();
        Empleado empleado = operacionEmpleado.buscarEmpleadoPorDocumento(documento, todosLosEmpleados);

        if (empleado != null) {
            double sueldo = operacionEmpleado.calcularSueldoEmpleado(empleado);
            System.out.println("El sueldo de " + empleado.getNombre() + " es: " + String.format("%.2f", sueldo));
        } else {
            System.out.println("Empleado con documento " + documento + " no encontrado.");
        }
    }

    private static void contarEmpleadosEnEmpresa() {
        System.out.println("\n--- CONTAR EMPLEADOS EN EMPRESA ---");
        System.out.print("Ingrese el NIT de la empresa: ");
        String nit = scanner.nextLine();
        Empresa empresa = operacionEmpresa.buscarEmpresaPorNit(nit, todasLasEmpresas);

        if (empresa != null) {
            int cantidad = operacionEmpleado.contarEmpleadosEnEmpresa(empresa);
            System.out.println("La empresa '" + empresa.getNombre() + "' tiene " + cantidad + " empleado(s).");
        } else {
            System.out.println("Empresa con NIT " + nit + " no encontrada.");
        }
    }

    private static void actualizarEmpleado() {
        System.out.println("\n--- ACTUALIZAR EMPLEADO ---");
        System.out.print("Ingrese el documento del empleado a actualizar: ");
        String documento = scanner.nextLine();
        Empleado empExistente = operacionEmpleado.buscarEmpleadoPorDocumento(documento, todosLosEmpleados);

        if (empExistente == null) {
            System.out.println("Empleado con documento " + documento + " no encontrado.");
            return;
        }

        System.out.println("Empleado actual: " + empExistente.toString());
        System.out.print("Ingrese nuevo nombre (dejar vacío para mantener): ");
        String nombre = scanner.nextLine();
        
        double sueldoHora = empExistente.getSueldoHora();
        int horasTrabajadas = empExistente.getHorasTrabajadas();
        boolean entradaValida;

        do {
            entradaValida = true;
            try {
                System.out.print("Ingrese nuevo sueldo por hora (0 para mantener): ");
                String inputSueldo = scanner.nextLine();
                if (!inputSueldo.isEmpty() && Double.parseDouble(inputSueldo) > 0) {
                    sueldoHora = Double.parseDouble(inputSueldo);
                } else if (!inputSueldo.isEmpty() && Double.parseDouble(inputSueldo) <= 0) {
                    System.out.println("Sueldo por hora debe ser positivo o vacío para mantener.");
                    entradaValida = false;
                }

                System.out.print("Ingrese nuevas horas trabajadas (0 para mantener): ");
                String inputHoras = scanner.nextLine();
                if (!inputHoras.isEmpty() && Integer.parseInt(inputHoras) >= 0) {
                    horasTrabajadas = Integer.parseInt(inputHoras);
                } else if (!inputHoras.isEmpty() && Integer.parseInt(inputHoras) < 0) {
                    System.out.println("Horas trabajadas deben ser positivas o cero, o vacío para mantener.");
                    entradaValida = false;
                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
                entradaValida = false;
            }
        } while (!entradaValida);
       
        // Se puede añadir lógica para cambiar el área si es un GestorProyectos
        if (empExistente instanceof GestorProyectos) {
            GestorProyectos gp = (GestorProyectos) empExistente;
            System.out.print("Ingrese nueva área (dejar vacío para mantener, actual: " + gp.getArea() + "): ");
            String area = scanner.nextLine();
            if (!area.isEmpty()) {
                gp.setArea(area);
            }
        }

        // Crear un empleado temporal con los datos actualizados
        // No se puede cambiar el tipo de empleado en este método sin más lógica
        Empleado empleadoTemp;
        if (empExistente instanceof Desarrollador) {
            empleadoTemp = new Desarrollador(documento, nombre.isEmpty() ? empExistente.getNombre() : nombre, sueldoHora, horasTrabajadas);
        } else if (empExistente instanceof GestorProyectos) {
            empleadoTemp = new GestorProyectos(documento, nombre.isEmpty() ? empExistente.getNombre() : nombre, sueldoHora, horasTrabajadas, ((GestorProyectos) empExistente).getArea());
        } else if (empExistente instanceof Admin) {
            empleadoTemp = new Admin(documento, nombre.isEmpty() ? empExistente.getNombre() : nombre, sueldoHora, horasTrabajadas);
        } else {
             empleadoTemp = new Empleado(documento, nombre.isEmpty() ? empExistente.getNombre() : nombre, sueldoHora, horasTrabajadas) {}; // Anon class for abstract
        }
        
        operacionEmpleado.actualizarEmpleado(documento, empleadoTemp, todosLosEmpleados);
    }

    private static void eliminarEmpleado() {
        System.out.println("\n--- ELIMINAR EMPLEADO ---");
        System.out.print("Ingrese el documento del empleado a eliminar: ");
        String documento = scanner.nextLine();
        operacionEmpleado.eliminarEmpleado(documento, todosLosEmpleados);
    }
}