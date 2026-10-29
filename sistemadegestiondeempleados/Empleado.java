package sistemadegestiondeempleados;

public abstract class Empleado {

    protected String codigo;
    protected String nombre;
    protected double salarioBase;

    public Empleado(String codigo, String nombre, double salarioBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public abstract double calcularSalario();
}