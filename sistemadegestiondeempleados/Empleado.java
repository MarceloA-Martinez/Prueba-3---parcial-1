package sistemadegestiondeempleados;

public abstract class Empleado implements Cloneable {

    private String codigo;
    private String nombre;
    private double salarioBase;

    public Empleado(String codigo, String nombre, double salarioBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public abstract double calcularSalario();

    public String obtenerCategoria() {
        double salario = calcularSalario();

        // Categorías definidas para esta práctica.
        if (salario < 10000) {
            return "Básica";
        } else if (salario < 20000) {
            return "Intermedia";
        } else {
            return "Alta";
        }
    }

    public String mostrarInformacion() {
        return "Código: " + codigo
                + "\nNombre: " + nombre
                + "\nSalario base: " + salarioBase
                + "\nSalario calculado: " + calcularSalario()
                + "\nCategoría: " + obtenerCategoria();
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("No se pudo clonar el empleado.");
        }
    }
}