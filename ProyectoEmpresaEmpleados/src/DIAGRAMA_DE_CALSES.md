```mermaid
classDiagram
    direction LR

    %% Parte a: Clases de Empleados
    class Empleado {
        <<abstract>>
        -String documento
        -String nombre
        -double sueldoHora
        -int horasTrabajadas
        +Empleado(String documento, String nombre, double sueldoHora, int horasTrabajadas)
        +Empleado()
        +getDocumento(): String
        +setDocumento(String documento): void
        +getNombre(): String
        +setNombre(String nombre): void
        +getSueldoHora(): double
        +setSueldoHora(double sueldoHora): void
        +getHorasTrabajadas(): int
        +setHorasTrabajadas(int horasTrabajadas): void
        +calcularSalario(): double
    }

    class Desarrollador {
        +Desarrollador(String documento, String nombre, double sueldoHora, int horasTrabajadas)
    }
    class GestorProyectos {
        -String area
        +GestorProyectos(String documento, String nombre, double sueldoHora, int horasTrabajadas, String area)
        +getArea(): String
        +setArea(String area): void
    }
    class Admin {
        +Admin(String documento, String nombre, double sueldoHora, int horasTrabajadas)
    }

    %% Relación de Herencia Empleado
    Empleado <|-- Desarrollador
    Empleado <|-- GestorProyectos
    Empleado <|-- Admin

    %% Parte b: Clases de Empresa
    class Empresa {
        -String nit
        -String nombre
        -String direccion
        -String ciudad
        +Empresa(String nit, String nombre, String direccion, String ciudad)
        +getNit(): String
        +setNit(String nit): void
        +getNombre(): String
        +setNombre(String nombre): void
        +getDireccion(): String
        +setDireccion(String direccion): void
        +getCiudad(): String
        +setCiudad(String ciudad): void
    }

    class EmpresaDesarrollo {
        +EmpresaDesarrollo(String nit, String nombre, String direccion, String ciudad)
    }

    %% Relación de Herencia Empresa
    Empresa <|-- EmpresaDesarrollo

    %% Parte c: Relación entre Empleado y Empresa
    Empleado "1" --> "1" Empresa : trabaja en
