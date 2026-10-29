package sistemadegestiondeempleados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

    private ArrayList<Empleado> empleados = new ArrayList<>();

    private JTextField txtCodigo = new JTextField();
    private JTextField txtNombre = new JTextField();
    private JTextField txtSalarioBase = new JTextField();
    private JTextField txtBonificacion = new JTextField();
    private JTextField txtDescuento = new JTextField();
    private JTextField txtHoras = new JTextField();
    private JTextField txtTarifa = new JTextField();
    private JTextField txtBuscar = new JTextField();

    private JComboBox<String> cmbTipo = new JComboBox<>(
            new String[]{"Administrativo", "Por horas"});

    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaPrincipal() {
        setTitle("Sistema de Gestión de Empleados");
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();
    }

    private void crearInterfaz() {
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                "SISTEMA DE GESTIÓN DE EMPLEADOS",
                SwingConstants.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.setBorder(BorderFactory.createTitledBorder(
                "Datos del empleado"));

        formulario.add(new JLabel("Código:"));
        formulario.add(txtCodigo);

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Tipo de empleado:"));
        formulario.add(cmbTipo);

        formulario.add(new JLabel("Salario base:"));
        formulario.add(txtSalarioBase);

        formulario.add(new JLabel("Bonificación (administrativo):"));
        formulario.add(txtBonificacion);

        formulario.add(new JLabel("Descuento % (administrativo):"));
        formulario.add(txtDescuento);

        formulario.add(new JLabel("Horas trabajadas (por horas):"));
        formulario.add(txtHoras);

        formulario.add(new JLabel("Tarifa por hora (por horas):"));
        formulario.add(txtTarifa);

        JPanel busqueda = new JPanel(new BorderLayout(5, 5));
        busqueda.setBorder(BorderFactory.createTitledBorder("Buscar"));
        busqueda.add(txtBuscar, BorderLayout.CENTER);

        JButton btnBuscar = new JButton("Buscar por código");
        busqueda.add(btnBuscar, BorderLayout.EAST);

        JPanel superior = new JPanel(new BorderLayout(5, 5));
        superior.add(formulario, BorderLayout.CENTER);
        superior.add(busqueda, BorderLayout.SOUTH);

        modelo = new DefaultTableModel(
                new String[]{"Código", "Nombre", "Tipo", "Salario", "Categoría"},
                0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tabla.setRowHeight(24);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Empleados registrados"));

        JPanel centro = new JPanel(new BorderLayout(8, 8));
        centro.add(superior, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnClonar = new JButton("Clonar seleccionado");
        JButton btnComparar = new JButton("Comparar dos");
        JButton btnEstadisticas = new JButton("Estadísticas");

        botones.add(btnRegistrar);
        botones.add(btnLimpiar);
        botones.add(btnClonar);
        botones.add(btnComparar);
        botones.add(btnEstadisticas);

        add(botones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrarEmpleado());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> buscarEmpleado());
        btnClonar.addActionListener(e -> clonarEmpleado());
        btnComparar.addActionListener(e -> compararEmpleados());
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
    }

    private void registrarEmpleado() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty()) {
            mostrarError("Debes ingresar el código y el nombre.");
            return;
        }

        if (buscarPorCodigo(codigo) != null) {
            mostrarError("Ya existe un empleado con ese código.");
            return;
        }

        double salarioBase;
        double bonificacion = 0;
        double descuento = 0;
        double tarifa = 0;
        int horas = 0;

        try {
            salarioBase = Double.parseDouble(
                    txtSalarioBase.getText().trim());

            if (salarioBase < 0) {
                mostrarError("El salario base no puede ser negativo.");
                return;
            }

            if (cmbTipo.getSelectedIndex() == 0) {
                bonificacion = Double.parseDouble(
                        txtBonificacion.getText().trim());

                descuento = Double.parseDouble(
                        txtDescuento.getText().trim());

                if (bonificacion < 0) {
                    mostrarError("La bonificación no puede ser negativa.");
                    return;
                }

                if (descuento < 0 || descuento > 100) {
                    mostrarError("El descuento debe estar entre 0 y 100.");
                    return;
                }

            } else {
                horas = Integer.parseInt(txtHoras.getText().trim());

                tarifa = Double.parseDouble(
                        txtTarifa.getText().trim());

                if (horas < 0 || tarifa < 0) {
                    mostrarError("Las horas y la tarifa no pueden ser negativas.");
                    return;
                }
            }

        } catch (NumberFormatException ex) {
            mostrarError("Revisa los números. No dejes campos numéricos vacíos.");
            return;
        }

        Empleado empleado;

        if (cmbTipo.getSelectedIndex() == 0) {
            empleado = new EmpleadoAdministrativo(
                    codigo, nombre, salarioBase, bonificacion, descuento);
        } else {
            empleado = new EmpleadoPorHoras(
                    codigo, nombre, salarioBase, horas, tarifa);
        }

        empleados.add(empleado);
        agregarFila(empleado);

        JOptionPane.showMessageDialog(this,
                "Empleado registrado correctamente.");

        limpiarFormulario();
    }

    private void agregarFila(Empleado empleado) {
        String tipo = empleado instanceof EmpleadoAdministrativo
                ? "Administrativo" : "Por horas";

        modelo.addRow(new Object[]{
            empleado.getCodigo(),
            empleado.getNombre(),
            tipo,
            String.format("%.2f", empleado.calcularSalario()),
            empleado.obtenerCategoria()
        });
    }

    private Empleado buscarPorCodigo(String codigo) {
        for (Empleado empleado : empleados) {
            if (empleado.getCodigo().equalsIgnoreCase(codigo)) {
                return empleado;
            }
        }

        return null;
    }

    private void buscarEmpleado() {
        String codigo = txtBuscar.getText().trim();

        if (codigo.isEmpty()) {
            mostrarError("Escribe el código que quieres buscar.");
            return;
        }

        for (int i = 0; i < modelo.getRowCount(); i++) {
            String codigoTabla = modelo.getValueAt(i, 0).toString();

            if (codigoTabla.equalsIgnoreCase(codigo)) {
                tabla.setRowSelectionInterval(i, i);
                tabla.scrollRectToVisible(
                        tabla.getCellRect(i, 0, true));

                Empleado empleado = buscarPorCodigo(codigo);

                JOptionPane.showMessageDialog(this,
                        empleado.mostrarInformacion(),
                        "Empleado encontrado",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(this,
                "No se encontró un empleado con ese código.");
    }

    private void clonarEmpleado() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            mostrarError("Selecciona un empleado para clonarlo.");
            return;
        }

        Empleado original = buscarPorCodigo(
                modelo.getValueAt(fila, 0).toString());

        if (original == null) {
            mostrarError("No se encontró el empleado seleccionado.");
            return;
        }

        Empleado copia = (Empleado) original.clone();

        String nuevoCodigo;

        do {
            nuevoCodigo = JOptionPane.showInputDialog(this,
                    "Escribe un código nuevo para la copia:");
            
            if (nuevoCodigo == null) {
                return;
            }

            nuevoCodigo = nuevoCodigo.trim();

            if (nuevoCodigo.isEmpty()) {
                mostrarError("El código no puede estar vacío.");
            } else if (buscarPorCodigo(nuevoCodigo) != null) {
                mostrarError("Ese código ya existe. Escribe otro.");
            } else {
                break;
            }

        } while (true);

        copia.setCodigo(nuevoCodigo);

        empleados.add(copia);
        agregarFila(copia);

        JOptionPane.showMessageDialog(this,
                "Empleado clonado y registrado correctamente.");
    }

    private void compararEmpleados() {
        int[] filas = tabla.getSelectedRows();

        if (filas.length != 2) {
            mostrarError("Selecciona exactamente dos empleados en la tabla.");
            return;
        }

        Empleado primero = buscarPorCodigo(
                modelo.getValueAt(filas[0], 0).toString());

        Empleado segundo = buscarPorCodigo(
                modelo.getValueAt(filas[1], 0).toString());

        boolean mismoObjeto = primero == segundo;

        boolean mismosDatos =
                primero.getCodigo().equals(segundo.getCodigo())
                && primero.getNombre().equals(segundo.getNombre())
                && primero.getSalarioBase() == segundo.getSalarioBase()
                && primero.calcularSalario() == segundo.calcularSalario()
                && primero.getClass().equals(segundo.getClass());

        JOptionPane.showMessageDialog(this,
                "¿Es el mismo objeto? " + (mismoObjeto ? "Sí" : "No")
                + "\n¿Coinciden los datos comparados? "
                + (mismosDatos ? "Sí" : "No"));
    }

    private void mostrarEstadisticas() {
        if (empleados.isEmpty()) {
            mostrarError("Todavía no hay empleados registrados.");
            return;
        }

        Empleado mayor = empleados.get(0);
        double suma = 0;

        for (Empleado empleado : empleados) {
            double salario = empleado.calcularSalario();
            suma += salario;

            if (salario > mayor.calcularSalario()) {
                mayor = empleado;
            }
        }

        double promedio = suma / empleados.size();

        JOptionPane.showMessageDialog(this,
                "Cantidad de empleados: " + empleados.size()
                + "\nEmpleado con mayor salario: " + mayor.getNombre()
                + "\nCódigo: " + mayor.getCodigo()
                + "\nSalario más alto: "
                + String.format("%.2f", mayor.calcularSalario())
                + "\nSalario promedio: "
                + String.format("%.2f", promedio),
                "Estadísticas",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtSalarioBase.setText("");
        txtBonificacion.setText("");
        txtDescuento.setText("");
        txtHoras.setText("");
        txtTarifa.setText("");
        txtBuscar.setText("");

        cmbTipo.setSelectedIndex(0);
        tabla.clearSelection();

        txtCodigo.requestFocus();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this,
                mensaje,
                "Revisar datos",
                JOptionPane.WARNING_MESSAGE);
    }
}