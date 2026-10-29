package sistemadegestiondeempleados;

public class EmpleadoPorHoras extends Empleado {

    private int horas;
    private double tarifa;

    public EmpleadoPorHoras(String codigo, String nombre,
            double salarioBase, int horas, double tarifa) {

        super(codigo, nombre, salarioBase);
        this.horas = horas;
        this.tarifa = tarifa;
    }

    @Override
    public double calcularSalario() {
        return salarioBase + horas * tarifa;
    }
}