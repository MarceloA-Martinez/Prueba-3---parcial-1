package sistemagestiondeempleados;

public class EmpleadoAdministrativo extends Empleado {

    private double bonificacion;
    private double descuento;

    public EmpleadoAdministrativo(
            String codigo,
            String nombre,
            double salarioBase,
            double bonificacion,
            double descuento) {

        super(codigo, nombre, salarioBase);

        setBonificacion(bonificacion);
        setDescuento(descuento);
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        if (!Double.isFinite(bonificacion) || bonificacion < 0) {
            throw new IllegalArgumentException(
                    "La bonificacion no puede ser negativa."
            );
        }

        this.bonificacion = bonificacion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        if (!Double.isFinite(descuento)
                || descuento < 0 || descuento > 100) {
            throw new IllegalArgumentException(
                    "El descuento debe estar entre 0 y 100."
            );
        }

        this.descuento = descuento;
    }

    @Override
    public double calcularSalario() {
        return (getSalarioBase() + bonificacion)
                * (1 - descuento / 100.0);
    }
}