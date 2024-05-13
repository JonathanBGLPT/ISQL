package interfaz.panelPrincipal.subPaneles;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelMostarDatos extends JPanel {
    
    private PanelPrincipal panelPrincipal;
    private ArrayList<String[]> datosTabla;
    private JPanel panelContenedorDatos;
    private JPanel panelCabecera;
    
    public PanelMostarDatos (PanelPrincipal panelPrin, ArrayList<String[]> datos) {

        Auxiliar.calcularSize(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
        setLayout(null);

        panelPrincipal = panelPrin;
        datosTabla = datos;

        // Declaracion del contenedor de datos
        panelContenedorDatos = new JPanel();
        panelContenedorDatos.setLayout(new BoxLayout(panelContenedorDatos, BoxLayout.Y_AXIS));

        // Scroll con los campos de la tabla
        JPanel panelContenedorCampos = new JPanel();
        panelContenedorCampos.setLayout(new BoxLayout(panelContenedorCampos, BoxLayout.Y_AXIS));
        ArrayList<String[]> campos = Auxiliar.conexionSQL.obtenerCamposTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
        panelCabecera = new JPanel();
        panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.X_AXIS));
        if (!panelPrincipal.panelGestionTabla.nombreTablaSeleccionada.equals("")) {

            panelCabecera.add(Box.createHorizontalStrut(5));
            JCheckBox seleccionarTodasLasFilasEstaSeleccionado = new JCheckBox();
            seleccionarTodasLasFilasEstaSeleccionado.setVisible(false);
            panelCabecera.add(seleccionarTodasLasFilasEstaSeleccionado);
    
            JButton seleccionarTodasLasFilas = new JButton();
            seleccionarTodasLasFilas.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/checkNO.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.03), (int)(Auxiliar.dimensionVentana.getHeight()*0.03), Image.SCALE_SMOOTH)));
            seleccionarTodasLasFilas.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.03), (int)(getSize().getWidth()*0.03)));
            seleccionarTodasLasFilas.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.03), (int)(getSize().getWidth()*0.03)));
            seleccionarTodasLasFilas.addActionListener(accion -> {

                seleccionarTodasLasFilasEstaSeleccionado.setSelected(!seleccionarTodasLasFilasEstaSeleccionado.isSelected());
                seleccionarTodasLasFilas.setIcon(new ImageIcon(new ImageIcon(getClass().getResource((seleccionarTodasLasFilasEstaSeleccionado.isSelected())? "/checkSI.png" : "/checkNO.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.03), (int)(Auxiliar.dimensionVentana.getHeight()*0.03), Image.SCALE_SMOOTH)));

                for (int f = 1; f < panelContenedorDatos.getComponentCount(); f++) {

                    JPanel panelFila = (JPanel)panelContenedorDatos.getComponent(f);
                    ((JCheckBox)panelFila.getComponent(1)).setSelected(seleccionarTodasLasFilasEstaSeleccionado.isSelected());
                    ((JButton)panelFila.getComponent(2)).setIcon(new ImageIcon(new ImageIcon(getClass().getResource((seleccionarTodasLasFilasEstaSeleccionado.isSelected())? "/checkSI.png" : "/checkNO.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.03), (int)(Auxiliar.dimensionVentana.getHeight()*0.03), Image.SCALE_SMOOTH)));
                }
            });
            panelCabecera.add(seleccionarTodasLasFilas);
            panelCabecera.add(Box.createHorizontalStrut(5));

            for (String[] campo : campos) {

                JLabel nombreCampo = new JLabel("<html>"+ campo[0] + "</html>");
                nombreCampo.setHorizontalAlignment(JLabel.CENTER);
                nombreCampo.setFont(Auxiliar.fuentePequenia);
                nombreCampo.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
                nombreCampo.setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 1));
                panelCabecera.add(nombreCampo);
            }
        }
        JScrollPane panelMostarCampos = new JScrollPane(panelCabecera);
        Auxiliar.calcularSize(getSize(), panelMostarCampos, 0.978, 0.05);
        Auxiliar.calcularLocation(getSize(), panelMostarCampos, 0.005, 0.005);
        panelMostarCampos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelMostarCampos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        panelMostarCampos.getVerticalScrollBar().setUnitIncrement(0);
        add(panelMostarCampos);

        // Scroll que muestra los datos de la tabla
        JPanel panelFilaVacia = new JPanel();
        panelFilaVacia.setLayout(new BoxLayout(panelFilaVacia, BoxLayout.X_AXIS));
        panelFilaVacia.setPreferredSize(new Dimension(16 + (int)(getSize().getWidth()*0.25*campos.size() + getSize().getWidth()*0.025), 0));
        panelContenedorDatos.add(panelFilaVacia);

        boolean datosValidos = !panelPrincipal.panelGestionTabla.nombreTablaSeleccionada.equals("") && datosTabla != null;
        for (int f = 0; datosValidos && f < datosTabla.size(); f++) {

            String[] fila = datosTabla.get(f);
            JPanel panelFila = new JPanel();
            panelFila.setLayout(new BoxLayout(panelFila, BoxLayout.X_AXIS));
            panelFila.setPreferredSize(new Dimension(16 + (int)(getSize().getWidth()*0.25*campos.size() + getSize().getWidth()*0.025), (int)(getSize().getHeight()*0.03)));
            panelFila.setMaximumSize(new Dimension(16 + (int)(getSize().getWidth()*0.25*campos.size() + getSize().getWidth()*0.025), (int)(getSize().getHeight()*0.03)));
            panelFila.setBackground((f % 2 == 0)? java.awt.Color.WHITE : java.awt.Color.LIGHT_GRAY);

            panelFila.add(Box.createHorizontalStrut(8));
            JCheckBox seleccionarFilaEstaSeleccionado = new JCheckBox();
            seleccionarFilaEstaSeleccionado.setVisible(false);
            panelFila.add(seleccionarFilaEstaSeleccionado);
            JButton seleccionarFila = new JButton();
            seleccionarFila.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/checkNO.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.03), (int)(Auxiliar.dimensionVentana.getHeight()*0.03), Image.SCALE_SMOOTH)));
            seleccionarFila.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.025), (int)(getSize().getWidth()*0.025)));
            seleccionarFila.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.025), (int)(getSize().getWidth()*0.025)));
            seleccionarFila.addActionListener(accion -> {

                seleccionarFilaEstaSeleccionado.setSelected(!seleccionarFilaEstaSeleccionado.isSelected());
                if (!seleccionarFilaEstaSeleccionado.isSelected()) {

                    ((JCheckBox)panelCabecera.getComponent(1)).setSelected(false);
                    ((JButton)panelCabecera.getComponent(2)).setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/checkNO.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.03), (int)(Auxiliar.dimensionVentana.getHeight()*0.03), Image.SCALE_SMOOTH)));
                }
                seleccionarFila.setIcon(new ImageIcon(new ImageIcon(getClass().getResource((seleccionarFilaEstaSeleccionado.isSelected())? "/checkSI.png" : "/checkNO.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.03), (int)(Auxiliar.dimensionVentana.getHeight()*0.03), Image.SCALE_SMOOTH)));
            });
            panelFila.add(seleccionarFila);
            panelFila.add(Box.createHorizontalStrut(8));

            for (String dato : fila) {

                if (dato.length() > 16) dato = dato.substring(0, 16) + "...";
                JLabel campo = new JLabel("<html>&#8203;" + dato + "</html>");
                campo.setFont(Auxiliar.fuentePequenia);
                if (dato.equals("")) {
                    
                    campo.setOpaque(true);
                    campo.setBackground(java.awt.Color.RED);
                }
                campo.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
                campo.setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 1));
                panelFila.add(campo);
            }
            panelContenedorDatos.add(panelFila);
        }
        JScrollPane panelMostarDatos = new JScrollPane(panelContenedorDatos);
        Auxiliar.calcularSize(getSize(), panelMostarDatos, 0.993, 0.943);
        Auxiliar.calcularLocation(getSize(), panelMostarDatos, 0.005, 0.055);
        panelMostarDatos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelMostarDatos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelMostarDatos.getHorizontalScrollBar().setUnitIncrement(20);
        panelMostarDatos.getVerticalScrollBar().setUnitIncrement(20);
        panelMostarDatos.getHorizontalScrollBar().addAdjustmentListener(accion -> {

            panelMostarCampos.getHorizontalScrollBar().setValue(panelMostarDatos.getHorizontalScrollBar().getValue());
        });
        add(panelMostarDatos);
    }

    public void eliminarDatos () {

        if (((JCheckBox)panelCabecera.getComponent(1)).isSelected()) {

            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar todos los datos de la tabla '" + panelPrincipal.panelGestionTabla.nombreTablaSeleccionada + "'?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {

                Auxiliar.conexionSQL.eliminarTodosLosDatos(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
                panelPrincipal.panelGestionTabla.datosMostrarTabla = Auxiliar.conexionSQL.obtenerTodosLosDatosTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
            };

        } else {

            int numeroDeEliminados = 0;
            String idsAEliminar = "\n";
            ArrayList<Integer> listaIds = new ArrayList<>();
            for (int f = 1; f < panelContenedorDatos.getComponentCount(); f++) {

                JPanel panelFila = (JPanel)panelContenedorDatos.getComponent(f);
                if (((JCheckBox)panelFila.getComponent(1)).isSelected()) {

                    numeroDeEliminados++;
                    String idText = ((JLabel)panelFila.getComponent(4)).getText().trim();
                    int id = Integer.parseInt(idText.substring(13, idText.length()-7));
                    listaIds.add(id);
                    idsAEliminar += id + ((numeroDeEliminados % 10 == 0)? ", \n" : ", ");
                }
            }
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar los datos de la tabla '" + panelPrincipal.panelGestionTabla.nombreTablaSeleccionada + "' con ids: " + idsAEliminar.substring(0, idsAEliminar.length()-2) + "?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {

                Auxiliar.conexionSQL.eliminarListaDeDatos(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada, listaIds);
                panelPrincipal.panelGestionTabla.datosMostrarTabla = Auxiliar.conexionSQL.obtenerTodosLosDatosTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
            };
        }
    }
}
