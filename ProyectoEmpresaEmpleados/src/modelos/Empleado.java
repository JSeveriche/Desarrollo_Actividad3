package modelos;

public abstract class Empleado {
    private String documento;
    private String nombre;
    private double sueldoHora;
    private int horasTrabajadas;
    private Empresa empresa; // Parte de la relación: un Empleado trabaja en una Empresa

    // Constructor con todos los atributos
    public Empleado(String documento, String nombre, double sueldoHora, int horasTrabajadas) {
        this.documento = documento;
        this.nombre = nombre;
        this.sueldoHora = sueldoHora;
        this.horasTrabajadas = horasTrabajadas;
        this.empresa = null; // Inicialmente no tiene empresa asignada
    }

    // Constructor vacío
    public Empleado() {
        this("", "", 0.0, 0); // Llama al constructor completo con valores por defecto
    }

    // Getters
    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSueldoHora() {
        return sueldoHora;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    // Setters
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSueldoHora(double sueldoHora) {
        this.sueldoHora = sueldoHora;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    // Setter para asignar la empresa
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    // Método para calcular el salario (puede ser abstracto si la lógica varía mucho)
    // Lo hacemos concreto aquí para una implementación base simple
    public double calcularSalario() {
        return sueldoHora * horasTrabajadas;
    }

    @Override
    public String toString() {
        String infoEmpresa = (empresa != null) ? empresa.getNombre() : "Sin asignar";
        return "Documento: " + documento +
               ", Nombre: " + nombre +
               ", Sueldo/Hora: " + String.format("%.2f", sueldoHora) +
               ", Horas Trabajadas: " + horasTrabajadas +
               ", Salario Calculado: " + String.format("%.2f", calcularSalario()) +
               ", Empresa: " + infoEmpresa;
    }
}