package interfaz.panelPrincipal;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import interfaz.Auxiliar;

public class PanelResumenTablas extends JPanel {

    private static final String ESPACIO_LABEL = "                                                                                                                                ";
    private PanelPrincipal panelPrincipal;
    public PanelResumenTablas (PanelPrincipal panelPrin) {

        setBackground(null); 
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, this, 0.2, 0.95);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0.005, 0.01);
        setLayout(null);

        panelPrincipal = panelPrin;
    }

    public void actualizarPanelResumenTablas () {

        removeAll();

        // Texto tablas
        JLabel textoTablas = new JLabel("Tablas:");
        textoTablas.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), textoTablas, 0.3, 0.03);
        Auxiliar.calcularLocation(getSize(), textoTablas, 0.025, 0.04);
        add(textoTablas);

        // Boton para crear una nueva tabla
        JButton botonCrearTabla = new JButton("Crear tabla");
        botonCrearTabla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCrearTabla.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonCrearTabla, 0.55, 0.05);
        Auxiliar.calcularLocation(getSize(), botonCrearTabla, 0.425, 0.02);
        botonCrearTabla.addActionListener(accion -> {

            String nombreTabla = null;
            boolean nombreNoRepetido = false;
            boolean nombreValidoLogitud = false;
            ArrayList<String> nombreTablas = Auxiliar.conexionSQL.obtenerNombreTablas();

            while (!nombreNoRepetido || !nombreValidoLogitud) {

                nombreTabla = JOptionPane.showInputDialog(null, "Ingrese el nombre de la nueva tabla:");
                if (nombreTabla != null) {

                    nombreValidoLogitud = nombreTabla.length() > 0 && nombreTabla.length() <= 16;
                    if (!nombreValidoLogitud) JOptionPane.showMessageDialog(null, "El nombre de la tabla debe contener entre 1 y 16 caracteres.");

                    nombreNoRepetido = true;
                    for (int t = 0; t < nombreTablas.size() && nombreNoRepetido; t++) nombreNoRepetido = !nombreTablas.get(t).equals(nombreTabla);
                    if (!nombreNoRepetido) JOptionPane.showMessageDialog(null, "La tabla introducida ya existe.");

                } else break;
            }

			if (nombreTabla != null) {

                Auxiliar.habilitacionDeBotones(panelPrincipal, false);
                panelPrincipal.panelGestionTabla.nombreTablaSeleccionada = nombreTabla;
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(1);
			}
		});
        add(botonCrearTabla);

        // Scroll donde aparecen todas las tablas
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
        ArrayList<String> nombreTablas = Auxiliar.conexionSQL.obtenerNombreTablas();
        
        boolean cambio = true;
        for (int t = 0; t < nombreTablas.size(); t++) {
            
            String nombreTabla = nombreTablas.get(t);
            JPanel panelTabla = new JPanel();
            panelTabla.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
            panelTabla.setBackground((cambio)? Color.WHITE : Color.LIGHT_GRAY); 
            JLabel labelNombreTabla = new JLabel("  " + nombreTabla.toUpperCase() + ESPACIO_LABEL);
            labelNombreTabla.setFont(Auxiliar.fuenteNormal.deriveFont(Font.BOLD));
            panelTabla.add(labelNombreTabla);

            // Agrego los campos
            ArrayList<String[]> listaCampos = Auxiliar.conexionSQL.obtenerCamposTabla(nombreTabla);
            for (String[] campoDesglosado : listaCampos) {

                JLabel campo = new JLabel("   - " + campoDesglosado[0] + ": " + campoDesglosado[1] + (campoDesglosado[2].equals("")? "" : "(" + campoDesglosado[2] + ")"));
                campo.setFont(Auxiliar.fuentePequenia);
                panelTabla.add(campo);
            }

            // Al hacer click actualizo PanelGestionTablas
            panelTabla.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    
                    if (Auxiliar.botonesActivados && panelPrincipal.panelGestionTabla.nombreTablaSeleccionada != nombreTabla) {

                        panelPrincipal.panelGestionTabla.nombreTablaSeleccionada = nombreTabla;
                        panelPrincipal.panelGestionTabla.datosMostrarTabla = Auxiliar.conexionSQL.obtenerTodosLosDatosTabla(nombreTabla);
                        panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
                    }
                }
            });
            panelContenedor.add(panelTabla);
            cambio = !cambio;
        }
        JScrollPane panelScroll = new JScrollPane(panelContenedor);
        Auxiliar.calcularSize(getSize(), panelScroll, 0.95, 0.925);
        Auxiliar.calcularLocation(getSize(), panelScroll, 0.025, 0.075);
        panelScroll.getVerticalScrollBar().setUnitIncrement(20);
        panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelScroll.setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
        add(panelScroll);

        revalidate();
        repaint();
    }
}
