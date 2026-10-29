package sistemadegestiondeempleados;

import javax.swing.SwingUtilities;

public class Sistemadegestiondeempleados {

    public static void Sistemadegestiondeempleados(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}