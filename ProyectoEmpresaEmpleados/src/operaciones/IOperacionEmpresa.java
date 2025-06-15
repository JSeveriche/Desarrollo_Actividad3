package operaciones;

import modelos.Empresa;
import modelos.Empleado;
import java.util.List;

public interface IOperacionEmpresa {
    // Método para agregar una empresa
    void agregarEmpresa(Empresa empresa, List<Empresa> todasLasEmpresas);

    // Método para consultar todas las empresas
    void listarTodasLasEmpresas(List<Empresa> todasLasEmpresas);

    // Método para buscar una empresa por NIT
    Empresa buscarEmpresaPorNit(String nit, List<Empresa> todasLasEmpresas);

    // Método para asignar un empleado a una empresa (si no está ya asignado)
    void asignarEmpleadoAEmpresa(Empleado empleado, Empresa empresa);

    // Otros métodos que puedan ser necesarios
    void actualizarEmpresa(String nit, Empresa empresaActualizada, List<Empresa> todasLasEmpresas);
    void eliminarEmpresa(String nit, List<Empresa> todasLasEmpresas);
}