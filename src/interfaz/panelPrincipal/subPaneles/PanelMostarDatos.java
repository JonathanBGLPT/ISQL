package interfaz.panelPrincipal.subPaneles;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelMostarDatos extends JPanel {
    
    private PanelPrincipal panelPrincipal;
    private ArrayList<String[]> datosTabla;
    private ArrayList<String> columnasEliminadas;
    private Map<Integer,JPanel> mapaFilasCambiadas;
    private JPanel panelContenedorDatos;
    private JPanel panelCabecera;
    
    private ImageIcon checkSI;
    private ImageIcon checkNO;

    public PanelMostarDatos (PanelPrincipal panelPrin, ArrayList<String[]> datos, ArrayList<String> columnasBorradas) {

        Auxiliar.calcularSize(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.colorGrisFondo, 2));
        setBackground(Auxiliar.colorGrisFondo);
        setLayout(null);

        checkSI = new ImageIcon(new ImageIcon(getClass().getResource("/checkSI.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.03), (int)(Auxiliar.dimensionVentana.getHeight()*0.03), Image.SCALE_SMOOTH));
        checkNO = new ImageIcon(new ImageIcon(getClass().getResource("/checkNO.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.03), (int)(Auxiliar.dimensionVentana.getHeight()*0.03), Image.SCALE_SMOOTH));

        panelPrincipal = panelPrin;
        datosTabla = datos;
        columnasEliminadas = columnasBorradas;
        mapaFilasCambiadas = new HashMap<>();

        Set<String> setColumnasEliminadas = new HashSet<>();
        if (columnasEliminadas != null) {

            for (String columna : columnasEliminadas) setColumnasEliminadas.add(columna);
        }

        // Declaracion del contenedor de datos
        panelContenedorDatos = new JPanel();
        panelContenedorDatos.setLayout(new BoxLayout(panelContenedorDatos, BoxLayout.Y_AXIS));
        panelContenedorDatos.setBackground(Auxiliar.colorGrisClaro);

        // Scroll con los campos de la tabla
        ArrayList<String[]> campos = Auxiliar.conexionSQL.obtenerCamposTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
        panelCabecera = new JPanel();
        panelCabecera.setBackground(Auxiliar.colorGrisClaro);
        panelCabecera.setPreferredSize(new Dimension(16 + (int)(getSize().getWidth()*0.25*(campos.size()-setColumnasEliminadas.size()) + getSize().getWidth()*0.025), (int)(getSize().getHeight()*0.03)));
        panelCabecera.setMaximumSize(new Dimension(16 + (int)(getSize().getWidth()*0.25*(campos.size()-setColumnasEliminadas.size()) + getSize().getWidth()*0.025), (int)(getSize().getHeight()*0.03)));
        panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.X_AXIS));
        panelCabecera.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (!panelPrincipal.panelGestionTabla.nombreTablaSeleccionada.equals("")) {

            panelCabecera.add(Box.createHorizontalStrut(6));
            JCheckBox seleccionarTodasLasFilasEstaSeleccionado = new JCheckBox();
            seleccionarTodasLasFilasEstaSeleccionado.setVisible(false);
            panelCabecera.add(seleccionarTodasLasFilasEstaSeleccionado);
    
            JButton seleccionarTodasLasFilas = new JButton();
            seleccionarTodasLasFilas.setCursor(new Cursor(Cursor.HAND_CURSOR));
            seleccionarTodasLasFilas.setIcon(checkNO);
            seleccionarTodasLasFilas.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.03), (int)(getSize().getWidth()*0.03)));
            seleccionarTodasLasFilas.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.03), (int)(getSize().getWidth()*0.03)));
            seleccionarTodasLasFilas.addActionListener(accion -> {

                seleccionarTodasLasFilasEstaSeleccionado.setSelected(!seleccionarTodasLasFilasEstaSeleccionado.isSelected());
                seleccionarTodasLasFilas.setIcon((seleccionarTodasLasFilasEstaSeleccionado.isSelected())? checkSI : checkNO);

                for (int f = 1; f < panelContenedorDatos.getComponentCount(); f++) {

                    JPanel panelFila = (JPanel)panelContenedorDatos.getComponent(f);
                    ((JCheckBox)panelFila.getComponent(1)).setSelected(seleccionarTodasLasFilasEstaSeleccionado.isSelected());
                    ((JButton)panelFila.getComponent(2)).setIcon((seleccionarTodasLasFilasEstaSeleccionado.isSelected())? checkSI : checkNO);
                }
            });
            panelCabecera.add(seleccionarTodasLasFilas);
            panelCabecera.add(Box.createHorizontalStrut(6));

            for (String[] campo : campos) {

                JLabel nombreCampo = new JLabel("<html>"+ campo[0] + "</html>");
                nombreCampo.setForeground(Auxiliar.colorLetra);
                nombreCampo.setHorizontalAlignment(JLabel.CENTER);
                nombreCampo.setFont(Auxiliar.fuentePequenia);
                nombreCampo.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
                nombreCampo.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
                nombreCampo.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 1));
                if (!setColumnasEliminadas.contains(campo[0])) panelCabecera.add(nombreCampo);
            }
        }
        JScrollPane panelMostarCampos = new JScrollPane(panelCabecera);
        panelMostarCampos.setBackground(Auxiliar.colorGrisFondo);
        panelMostarCampos.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
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
        panelContenedorDatos.setPreferredSize(new Dimension(16 + (int)(getSize().getWidth()*0.25*(campos.size()-setColumnasEliminadas.size()) + getSize().getWidth()*0.025), (int)(getSize().getHeight()*0.05*(datos == null? 1: datos.size()))));
        panelContenedorDatos.setMaximumSize(new Dimension(16 + (int)(getSize().getWidth()*0.25*(campos.size()-setColumnasEliminadas.size()) + getSize().getWidth()*0.025), (int)(getSize().getHeight()*0.05*(datos == null? 1: datos.size()))));
        panelContenedorDatos.add(panelFilaVacia);

        boolean datosValidos = !panelPrincipal.panelGestionTabla.nombreTablaSeleccionada.equals("") && datosTabla != null;
        for (int f = 0; datosValidos && f < datosTabla.size(); f++) {

            Color colorFondo = (f % 2 == 0)? Auxiliar.colorGrisClaro : Auxiliar.colorGrisOscuro;
            String[] fila = datosTabla.get(f);
            JPanel panelFila = new JPanel();
            panelFila.setLayout(new BoxLayout(panelFila, BoxLayout.X_AXIS));
            panelFila.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelFila.setPreferredSize(new Dimension(16 + (int)(getSize().getWidth()*0.25*campos.size() + getSize().getWidth()*0.025), (int)(getSize().getHeight()*0.05)));
            panelFila.setMinimumSize(new Dimension(16 + (int)(getSize().getWidth()*0.25*campos.size() + getSize().getWidth()*0.025), (int)(getSize().getHeight()*0.05)));
            panelFila.setMaximumSize(new Dimension(16 + (int)(getSize().getWidth()*0.25*campos.size() + getSize().getWidth()*0.025), (int)(getSize().getHeight()*0.05)));
            panelFila.setBackground(colorFondo);

            panelFila.add(Box.createHorizontalStrut(8));
            JCheckBox seleccionarFilaEstaSeleccionado = new JCheckBox();
            seleccionarFilaEstaSeleccionado.setVisible(false);
            panelFila.add(seleccionarFilaEstaSeleccionado);
            JButton seleccionarFila = new JButton();
            seleccionarFila.setCursor(new Cursor(Cursor.HAND_CURSOR));
            seleccionarFila.setIcon(checkNO);
            seleccionarFila.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.025), (int)(getSize().getWidth()*0.025)));
            seleccionarFila.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.025), (int)(getSize().getWidth()*0.025)));
            seleccionarFila.addActionListener(accion -> {

                seleccionarFilaEstaSeleccionado.setSelected(!seleccionarFilaEstaSeleccionado.isSelected());
                if (!seleccionarFilaEstaSeleccionado.isSelected()) {

                    ((JCheckBox)panelCabecera.getComponent(1)).setSelected(false);
                    ((JButton)panelCabecera.getComponent(2)).setIcon(checkNO);
                }
                seleccionarFila.setIcon((seleccionarFilaEstaSeleccionado.isSelected())? checkSI : checkNO);
            });
            panelFila.add(seleccionarFila);
            panelFila.add(Box.createHorizontalStrut(8));

            JLabel campoId = new JLabel("<html>&#8203;" + fila[0] + "</html>");
            campoId.setForeground(Auxiliar.colorLetra);
            campoId.setFont(Auxiliar.fuentePequenia);
            campoId.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
            campoId.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
            campoId.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
            campoId.setBorder(BorderFactory.createLineBorder(Auxiliar.colorGrisFondo, 1));
            panelFila.add(campoId);

            for (int d = 1; d < fila.length; d++) {

                String dato = fila[d];
                JTextField campo = new JTextField(dato);
                campo.setForeground(Auxiliar.colorLetra);
                campo.setFont(Auxiliar.fuentePequenia);
                campo.setBackground(colorFondo);
                if (dato.equals("")) {
                    
                    campo.setOpaque(true);
                    campo.setBackground(Auxiliar.colorFaltaDato);
                }
                campo.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
                campo.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
                campo.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
                campo.setBorder(BorderFactory.createLineBorder(Auxiliar.colorGrisFondo, 1));
                campo.getDocument().addDocumentListener(new DocumentListener() {

                public void insertUpdate(DocumentEvent e) { agregarACambios(); }
                public void removeUpdate(DocumentEvent e) { agregarACambios(); }
                public void changedUpdate(DocumentEvent e) { agregarACambios(); }

                private void agregarACambios() {
                    
                    mapaFilasCambiadas.put(Integer.parseInt(fila[0]), panelFila);
                    seleccionarFilaEstaSeleccionado.setSelected(true);
                    seleccionarFila.setIcon(checkSI);
                }
            });
                panelFila.add(campo);
            }
            panelContenedorDatos.add(panelFila);
        }
        JScrollPane panelMostarDatos = new JScrollPane(panelContenedorDatos);
        panelMostarDatos.setBackground(Auxiliar.colorGrisFondo);
        panelMostarDatos.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        Auxiliar.ajustarScrollBar(panelMostarDatos.getHorizontalScrollBar());
        Auxiliar.ajustarScrollBar(panelMostarDatos.getVerticalScrollBar());
        Auxiliar.calcularSize(getSize(), panelMostarDatos, 0.993, 0.943);
        Auxiliar.calcularLocation(getSize(), panelMostarDatos, 0.005, 0.055);
        panelMostarDatos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelMostarDatos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelMostarDatos.getHorizontalScrollBar().addAdjustmentListener(accion -> {

            panelMostarCampos.getHorizontalScrollBar().setValue(panelMostarDatos.getHorizontalScrollBar().getValue());
        });
        add(panelMostarDatos);
    }

    public void eliminarDatos () {

        int numeroDeEliminados = 0;
        String idsAEliminar = "";
        ArrayList<Integer> listaIds = new ArrayList<>();
        ArrayList<JPanel> panelesEliminar = new ArrayList<>();
        for (int f = 1; f < panelContenedorDatos.getComponentCount(); f++) {

            JPanel panelFila = (JPanel)panelContenedorDatos.getComponent(f);
            if (((JCheckBox)panelFila.getComponent(1)).isSelected()) {

                numeroDeEliminados++;
                panelesEliminar.add(panelFila);
                String idText = ((JLabel)panelFila.getComponent(4)).getText().trim();
                int id = Integer.parseInt(idText.substring(13, idText.length()-7));
                listaIds.add(id);
                idsAEliminar += id + ((numeroDeEliminados % 10 == 0)? ", \n" : ", ");
            }
        }
        if (idsAEliminar.length() > 0) {

            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar los datos de la tabla '" + panelPrincipal.panelGestionTabla.nombreTablaSeleccionada + "' con ids:\n" + idsAEliminar.substring(0, idsAEliminar.length()-2) + "?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {

                Auxiliar.conexionSQL.eliminarListaDeDatos(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada, listaIds);
                panelPrincipal.panelGestionTabla.datosMostrarTabla = Auxiliar.conexionSQL.obtenerTodosLosDatosTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
                for (JPanel panelEliminado : panelesEliminar) panelContenedorDatos.remove(panelEliminado);
                for (int f = 1; f < panelContenedorDatos.getComponentCount(); f++) {

                    Color colorFondo = (f % 2 == 0)? Auxiliar.colorGrisOscuro : Auxiliar.colorGrisClaro;
                    JPanel panelFila = (JPanel)panelContenedorDatos.getComponent(f);
                    panelFila.setBackground(colorFondo);
                    for (int c = 5; c < panelFila.getComponentCount(); c++) {

                        JTextField campo = (JTextField)panelFila.getComponent(c);
                        campo.setBackground(colorFondo);
                        if (campo.getText().trim().equals("")) {

                            campo.setOpaque(true);
                            campo.setBackground(Auxiliar.colorFaltaDato);
                        }
                    }
                };
                mapaFilasCambiadas = new HashMap<>();
                revalidate();
                repaint();
            }
        } else JOptionPane.showMessageDialog(null, "No se ha marcado ningún dato para eliminar.");
    }

    public void actualizarDatos () {

        if (mapaFilasCambiadas.size() > 0) {

            String filasCambiadas = "";
            for (Integer id : mapaFilasCambiadas.keySet()) {

                if (((JCheckBox)mapaFilasCambiadas.get(id).getComponent(1)).isSelected()) filasCambiadas += id + ", ";
            }
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea modificar los datos de la tabla '" + panelPrincipal.panelGestionTabla.nombreTablaSeleccionada + "' con ids:\n" + filasCambiadas.substring(0, filasCambiadas.length()-2) + "?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {

                ArrayList<String> cabecera = new ArrayList<>();
                for (int l = 4; l < panelCabecera.getComponentCount(); l++) {

                    String campo = ((JLabel)panelCabecera.getComponent(l)).getText();
                    cabecera.add(campo.substring(6, campo.length()-7));
                }
                Auxiliar.conexionSQL.actualizarListaDeDatos(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada, mapaFilasCambiadas, cabecera);
                for (int f = 1; f < panelContenedorDatos.getComponentCount(); f++) {

                    Color colorFondo = (f % 2 == 0)? Auxiliar.colorGrisOscuro : Auxiliar.colorGrisClaro;

                    JPanel panelFila = (JPanel)panelContenedorDatos.getComponent(f);
                    panelFila.setBackground(colorFondo);
                    ((JCheckBox)panelFila.getComponent(1)).setSelected(false);
                    ((JButton)panelFila.getComponent(2)).setIcon(checkNO);
                    for (int c = 5; c < panelFila.getComponentCount(); c++) {

                        JTextField campo = (JTextField)panelFila.getComponent(c);
                        campo.setBackground(colorFondo);
                        if (campo.getText().trim().equals("")) {

                            campo.setOpaque(true);
                            campo.setBackground(Auxiliar.colorFaltaDato);
                        }
                    }
                }
                mapaFilasCambiadas = new HashMap<>();
            }

        } else JOptionPane.showMessageDialog(null, "No se ha modificado ningún dato.");
    }
}
