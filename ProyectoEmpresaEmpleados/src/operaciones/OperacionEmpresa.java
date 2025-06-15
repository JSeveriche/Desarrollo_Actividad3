package operaciones;

import modelos.Empresa;
import modelos.Empleado;
import java.util.List;
import java.util.Iterator; // Para eliminar de forma segura

public class OperacionEmpresa implements IOperacionEmpresa {

    @Override
    public void agregarEmpresa(Empresa empresa, List<Empresa> todasLasEmpresas) {
        if (empresa == null || buscarEmpresaPorNit(empresa.getNit(), todasLasEmpresas) != null) {
            System.out.println("Error: La empresa ya existe o es nula.");
            return;
        }
        todasLasEmpresas.add(empresa);
        System.out.println("Empresa '" + empresa.getNombre() + "' agregada con éxito.");
    }

    @Override
    public void listarTodasLasEmpresas(List<Empresa> todasLasEmpresas) {
        System.out.println("\n--- Listado de Todas las Empresas ---");
        if (todasLasEmpresas.isEmpty()) {
            System.out.println("No hay empresas registradas.");
            return;
        }
        for (Empresa emp : todasLasEmpresas) {
            System.out.println(emp.toString()); // Usa el toString sobrescrito
        }
    }

    @Override
    public Empresa buscarEmpresaPorNit(String nit, List<Empresa> todasLasEmpresas) {
        for (Empresa emp : todasLasEmpresas) {
            if (emp.getNit().equals(nit)) {
                return emp;
            }
        }
        return null; // Si no se encuentra
    }

    @Override
    public void asignarEmpleadoAEmpresa(Empleado empleado, Empresa empresa) {
        if (empleado == null || empresa == null) {
            System.out.println("Error: Empleado o empresa nula. No se puede asignar.");
            return;
        }
        if (empleado.getEmpresa() != null && empleado.getEmpresa() == empresa) {
             System.out.println("El empleado '" + empleado.getNombre() + "' ya está asignado a la empresa '" + empresa.getNombre() + "'.");
             return;
        }
        if (empleado.getEmpresa() != null) {
            // Si el empleado ya estaba en otra empresa, removerlo de esa empresa primero
            empleado.getEmpresa().removeEmpleado(empleado);
            System.out.println("El empleado '" + empleado.getNombre() + "' ha sido desvinculado de su empresa anterior.");
        }
        empresa.addEmpleado(empleado); // Esto también setea la empresa en el empleado
        System.out.println("Empleado '" + empleado.getNombre() + "' asignado a la empresa '" + empresa.getNombre() + "'.");
    }

    @Override
    public void actualizarEmpresa(String nit, Empresa empresaActualizada, List<Empresa> todasLasEmpresas) {
        Empresa empExistente = buscarEmpresaPorNit(nit, todasLasEmpresas);
        if (empExistente != null) {
            empExistente.setNombre(empresaActualizada.getNombre());
            empExistente.setDireccion(empresaActualizada.getDireccion());
            empExistente.setCiudad(empresaActualizada.getCiudad());
            System.out.println("Empresa con NIT " + nit + " actualizada.");
        } else {
            System.out.println("Empresa con NIT " + nit + " no encontrada para actualizar.");
        }
    }

    @Override
    public void eliminarEmpresa(String nit, List<Empresa> todasLasEmpresas) {
        Iterator<Empresa> iterator = todasLasEmpresas.iterator();
        while (iterator.hasNext()) {
            Empresa emp = iterator.next();
            if (emp.getNit().equals(nit)) {
                // Antes de eliminar la empresa, desvincular a todos sus empleados
                List<Empleado> empleadosDeEmpresa = new ArrayList<>(emp.getEmpleados()); // Copia para evitar ConcurrentModificationException
                for (Empleado empleado : empleadosDeEmpresa) {
                    emp.removeEmpleado(empleado); // Esto también desvincula del empleado
                }
                iterator.remove();
                System.out.println("Empresa con NIT " + nit + " eliminada y sus empleados desvinculados.");
                return;
            }
        }
        System.out.println("Empresa con NIT " + nit + " no encontrada para eliminar.");
    }
}