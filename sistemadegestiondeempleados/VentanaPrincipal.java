package sistemadegestiondeempleados;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame {

    private ArrayList<Empleado> empleados = new ArrayList<>();

    private JTextField txtCodigo = new JTextField();
    private JTextField txtNombre = new JTextField();
    private JTextField txtSalario = new JTextField();
    private JTextField txtBonificacion = new JTextField("0");
    private JTextField txtDescuento = new JTextField("0");
    private JTextField txtHoras = new JTextField("0");
    private JTextField txtTarifa = new JTextField("0");

    private JComboBox<String> cmbTipo = new JComboBox<>(
            new String[]{"Administrativo", "Por horas"}
    );

    private DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Codigo", "Nombre", "Tipo", "Salario"}, 0
    ) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    private JTable tabla = new JTable(modelo);

    public VentanaPrincipal() {

        setTitle("Sistema de Gestion de Empleados");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));

        formulario.add(new JLabel("Codigo:"));
        formulario.add(txtCodigo);

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Salario base:"));
        formulario.add(txtSalario);

        formulario.add(new JLabel("Tipo de empleado:"));
        formulario.add(cmbTipo);

        formulario.add(new JLabel("Bonificacion:"));
        formulario.add(txtBonificacion);

        formulario.add(new JLabel("Descuento (%):"));
        formulario.add(txtDescuento);

        formulario.add(new JLabel("Horas trabajadas:"));
        formulario.add(txtHoras);

        formulario.add(new JLabel("Tarifa por hora:"));
        formulario.add(txtTarifa);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCalcular = new JButton("Calcular salario");
        JButton btnLimpiar = new JButton("Limpiar");

        JPanel botones = new JPanel();

        botones.add(btnRegistrar);
        botones.add(btnCalcular);
        botones.add(btnLimpiar);

        JPanel superior = new JPanel(new BorderLayout());

        superior.add(formulario, BorderLayout.CENTER);
        superior.add(botones, BorderLayout.SOUTH);

        add(superior, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cmbTipo.addActionListener(e -> actualizarCampos());

        btnRegistrar.addActionListener(e -> registrarEmpleado());
        btnCalcular.addActionListener(e -> calcularSalario());
        btnLimpiar.addActionListener(e -> limpiar());

        actualizarCampos();
    }

    private void actualizarCampos() {

        boolean administrativo = cmbTipo.getSelectedIndex() == 0;

        txtBonificacion.setEnabled(administrativo);
        txtDescuento.setEnabled(administrativo);

        txtHoras.setEnabled(!administrativo);
        txtTarifa.setEnabled(!administrativo);
    }

    private void registrarEmpleado() {

        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            double salario = Double.parseDouble(txtSalario.getText());

            if (codigo.isEmpty() || nombre.isEmpty() || salario < 0) {
                JOptionPane.showMessageDialog(this,
                        "Completa los datos correctamente.");
                return;
            }

            for (Empleado e : empleados) {
                if (e.getCodigo().equalsIgnoreCase(codigo)) {
                    JOptionPane.showMessageDialog(this,
                            "Ese codigo ya existe.");
                    return;
                }
            }

            Empleado empleado;

            if (cmbTipo.getSelectedIndex() == 0) {

                double bonificacion = Double.parseDouble(
                        txtBonificacion.getText());

                double descuento = Double.parseDouble(
                        txtDescuento.getText());

                if (bonificacion < 0 || descuento < 0 || descuento > 100) {
                    JOptionPane.showMessageDialog(this,
                            "Revisa la bonificacion y el descuento.");
                    return;
                }

                empleado = new EmpleadoAdministrativo(
                        codigo, nombre, salario,
                        bonificacion, descuento);

            } else {

                int horas = Integer.parseInt(txtHoras.getText());
                double tarifa = Double.parseDouble(txtTarifa.getText());

                if (horas < 0 || tarifa < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Las horas y la tarifa no pueden ser negativas.");
                    return;
                }

                empleado = new EmpleadoPorHoras(
                        codigo, nombre, salario, horas, tarifa);
            }

            empleados.add(empleado);

            modelo.addRow(new Object[]{
                empleado.getCodigo(),
                empleado.getNombre(),
                cmbTipo.getSelectedItem(),
                String.format("%.2f", empleado.calcularSalario())
            });

            limpiar();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Introduce numeros validos.");
        }
    }

    private void calcularSalario() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un empleado de la tabla.");
            return;
        }

        Empleado empleado = empleados.get(
                tabla.convertRowIndexToModel(fila));

        JOptionPane.showMessageDialog(this,
                "Empleado: " + empleado.getNombre()
                + "\nSalario base: " + empleado.getSalarioBase()
                + "\nSalario calculado: " + empleado.calcularSalario());
    }

    private void limpiar() {

        txtCodigo.setText("");
        txtNombre.setText("");
        txtSalario.setText("");
        txtBonificacion.setText("0");
        txtDescuento.setText("0");
        txtHoras.setText("0");
        txtTarifa.setText("0");

        cmbTipo.setSelectedIndex(0);
    }
}