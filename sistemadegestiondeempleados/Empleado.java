package sistemagestiondeempleados;

public abstract class Empleado implements Cloneable {

    private String codigo;
    private String nombre;
    private double salarioBase;

    public Empleado(String codigo, String nombre, double salarioBase) {
        setCodigo(codigo);
        setNombre(nombre);
        setSalarioBase(salarioBase);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo es obligatorio.");
        }
        this.codigo = codigo.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (!Double.isFinite(salarioBase) || salarioBase < 0) {
            throw new IllegalArgumentException("Salario base invalido.");
        }
        this.salarioBase = salarioBase;
    }

    public abstract double calcularSalario();

    public String obtenerCategoria() {
        double salario = calcularSalario();

        if (salario < 15000) {
            return "Basica";
        } else if (salario < 25000) {
            return "Estandar";
        } else if (salario < 40000) {
            return "Superior";
        } else {
            return "Ejecutiva";
        }
    }

    public String mostrarInformacion() {
        return "Codigo: " + codigo
                + "\nNombre: " + nombre
                + "\nSalario base: " + salarioBase
                + "\nSalario calculado: " + calcularSalario()
                + "\nCategoria: " + obtenerCategoria();
    }

    @Override
    public Empleado clone() {
        try {
            return (Empleado) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
