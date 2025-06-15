package modelos;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private String nit;
    private String nombre;
    private String direccion;
    private String ciudad;
    private List<Empleado> empleados; // Relación con Empleado (para contar empleados)

    public Empresa(String nit, String nombre, String direccion, String ciudad) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.empleados = new ArrayList<>(); // Inicializa la lista de empleados
    }

    // Getters
    public String getNit() {
        return nit;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public List<Empleado> getEmpleados() { // Getter para acceder a la lista de empleados
        return empleados;
    }

    // Setters
    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    // Método para añadir un empleado a la empresa
    public void addEmpleado(Empleado empleado) {
        if (empleado != null && !this.empleados.contains(empleado)) {
            this.empleados.add(empleado);
            empleado.setEmpresa(this); // Asigna esta empresa al empleado
        }
    }

    // Método para remover un empleado de la empresa
    public void removeEmpleado(Empleado empleado) {
        if (empleado != null && this.empleados.remove(empleado)) {
            empleado.setEmpresa(null); // Desvincula la empresa del empleado
        }
    }

    @Override
    public String toString() {
        return "NIT: " + nit +
               ", Nombre: " + nombre +
               ", Dirección: " + direccion +
               ", Ciudad: " + ciudad +
               ", Empleados: " + empleados.size();
    }
}