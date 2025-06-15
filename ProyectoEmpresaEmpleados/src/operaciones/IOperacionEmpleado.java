package operaciones;

import modelos.Empleado;
import modelos.Empresa;
import java.util.List;

public interface IOperacionEmpleado {
    // Método para agregar un empleado a una empresa (y a la lista general)
    void agregarEmpleado(Empleado empleado, Empresa empresa, List<Empleado> todosLosEmpleados);

    // Método para buscar un empleado por su documento
    Empleado buscarEmpleadoPorDocumento(String documento, List<Empleado> todosLosEmpleados);

    // Método para listar todos los empleados
    void listarTodosLosEmpleados(List<Empleado> todosLosEmpleados);

    // Método para calcular el sueldo de un empleado específico
    double calcularSueldoEmpleado(Empleado empleado);

    // Método para contar empleados en una empresa específica
    int contarEmpleadosEnEmpresa(Empresa empresa);

    // Otros métodos que puedan ser necesarios
    void actualizarEmpleado(String documento, Empleado empleadoActualizado, List<Empleado> todosLosEmpleados);
    void eliminarEmpleado(String documento, List<Empleado> todosLosEmpleados);
}