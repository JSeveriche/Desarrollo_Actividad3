package operaciones;

import modelos.Empleado;
import modelos.Empresa;
import java.util.List;
import java.util.Iterator; // Para eliminar de forma segura

public class OperacionEmpleado implements IOperacionEmpleado {

    @Override
    public void agregarEmpleado(Empleado empleado, Empresa empresa, List<Empleado> todosLosEmpleados) {
        if (empleado == null || todosLosEmpleados.contains(empleado)) {
            System.out.println("Error: El empleado ya existe o es nulo.");
            return;
        }
        todosLosEmpleados.add(empleado);
        if (empresa != null) {
            empresa.addEmpleado(empleado); // Asigna el empleado a la empresa
            System.out.println("Empleado '" + empleado.getNombre() + "' agregado y asignado a empresa '" + empresa.getNombre() + "'.");
        } else {
            System.out.println("Empleado '" + empleado.getNombre() + "' agregado sin empresa asignada.");
        }
    }

    @Override
    public Empleado buscarEmpleadoPorDocumento(String documento, List<Empleado> todosLosEmpleados) {
        for (Empleado emp : todosLosEmpleados) {
            if (emp.getDocumento().equals(documento)) {
                return emp;
            }
        }
        return null; // Si no se encuentra
    }

    @Override
    public void listarTodosLosEmpleados(List<Empleado> todosLosEmpleados) {
        System.out.println("\n--- Listado de Todos los Empleados ---");
        if (todosLosEmpleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        for (Empleado emp : todosLosEmpleados) {
            System.out.println(emp.toString()); // Usa el toString sobrescrito
        }
    }

    @Override
    public double calcularSueldoEmpleado(Empleado empleado) {
        if (empleado == null) {
            System.out.println("Error: Empleado nulo. No se puede calcular el sueldo.");
            return 0.0;
        }
        return empleado.calcularSalario();
    }

    @Override
    public int contarEmpleadosEnEmpresa(Empresa empresa) {
        if (empresa == null) {
            System.out.println("Error: Empresa nula. No se pueden contar los empleados.");
            return 0;
        }
        return empresa.getEmpleados().size();
    }

    @Override
    public void actualizarEmpleado(String documento, Empleado empleadoActualizado, List<Empleado> todosLosEmpleados) {
        Empleado empExistente = buscarEmpleadoPorDocumento(documento, todosLosEmpleados);
        if (empExistente != null) {
            // Actualizar solo los campos que tienen sentido actualizar (no documento)
            empExistente.setNombre(empleadoActualizado.getNombre());
            empExistente.setSueldoHora(empleadoActualizado.getSueldoHora());
            empExistente.setHorasTrabajadas(empleadoActualizado.getHorasTrabajadas());
            // La empresa se gestiona por separado
            System.out.println("Empleado con documento " + documento + " actualizado.");
        } else {
            System.out.println("Empleado con documento " + documento + " no encontrado para actualizar.");
        }
    }

    @Override
    public void eliminarEmpleado(String documento, List<Empleado> todosLosEmpleados) {
        Iterator<Empleado> iterator = todosLosEmpleados.iterator();
        while (iterator.hasNext()) {
            Empleado emp = iterator.next();
            if (emp.getDocumento().equals(documento)) {
                // Si el empleado está asociado a una empresa, desvincularlo primero
                if (emp.getEmpresa() != null) {
                    emp.getEmpresa().removeEmpleado(emp);
                }
                iterator.remove();
                System.out.println("Empleado con documento " + documento + " eliminado.");
                return;
            }
        }
        System.out.println("Empleado con documento " + documento + " no encontrado para eliminar.");
    }
}