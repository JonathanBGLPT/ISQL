package interfaz.panelPrincipal.subPaneles;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelConsultaSimple extends JPanel {
    
    private static final String ESPACIO_LABEL = "                                                                                                                                ";
    private PanelPrincipal panelPrincipal;
    private JPanel panelContenedorFiltros;

    public PanelConsultaSimple (PanelPrincipal panelPrin) {

        Auxiliar.calcularSize(panelPrin.panelConsultas.panelDeConsultas.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelConsultas.panelDeConsultas.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
        setLayout(null);

        panelPrincipal = panelPrin;
        
        // Boton para cancelar la consulta
        JButton botonCancelarConsulta = new JButton("Cancelar");
        botonCancelarConsulta.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonCancelarConsulta, 0.485, 0.07);
        Auxiliar.calcularLocation(getSize(), botonCancelarConsulta, 0.01, 0.92);
        botonCancelarConsulta.addActionListener(accion -> {

            panelPrincipal.panelConsultas.elegirPanelConsulta(0);
        });
        add(botonCancelarConsulta);

        // Boton para realizar la consulta
        JButton botonRealizarConsulta = new JButton("Consultar");
        botonRealizarConsulta.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonRealizarConsulta, 0.485, 0.07);
        Auxiliar.calcularLocation(getSize(), botonRealizarConsulta, 0.505, 0.92);
        botonRealizarConsulta.addActionListener(accion -> {

            /// IMPLEMENTAR
        });
        add(botonRealizarConsulta);
        
        // Configuracion del panelContenedor y el Scroll
        panelContenedorFiltros = new JPanel();
        panelContenedorFiltros.setLayout(new BoxLayout(panelContenedorFiltros, BoxLayout.Y_AXIS));
        
        JScrollPane panelScroll = new JScrollPane(panelContenedorFiltros);
        Auxiliar.calcularSize(getSize(), panelScroll, 0.98, 0.90);
        Auxiliar.calcularLocation(getSize(), panelScroll, 0.01, 0.01);
        panelScroll.getVerticalScrollBar().setUnitIncrement(20);
        panelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(panelScroll);

        // Boton para agregar una tabla relacionada
        JPanel panelAgregarTabla = new JPanel();
        panelAgregarTabla.setBackground(Color.WHITE);
        panelAgregarTabla.setLayout(new BoxLayout(panelAgregarTabla, BoxLayout.Y_AXIS));
        panelAgregarTabla.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
        panelAgregarTabla.add(Box.createVerticalStrut(0));
        panelContenedorFiltros.add(panelAgregarTabla);
        
        ArrayList<String> nombreTablas = obtenerTablasRelacionadas();
        JComboBox<String> botonAgregarTabla = new JComboBox<>(nombreTablas.toArray(new String[0]));
        botonAgregarTabla.setRenderer(new ComboBoxRenderer());
        botonAgregarTabla.setFont(Auxiliar.fuenteNormal);
        botonAgregarTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        botonAgregarTabla.setPreferredSize(new Dimension((int)(getWidth()*0.65), (int)(getHeight()*0.05)));
        botonAgregarTabla.setMaximumSize(new Dimension((int)(getWidth()*0.65), (int)(getHeight()*0.05)));
        botonAgregarTabla.addActionListener(accion -> {
            
            String tablaSeleccionada = (String)botonAgregarTabla.getSelectedItem();
            agregarPanelTabla(tablaSeleccionada);

            nombreTablas.remove(tablaSeleccionada);
            if (nombreTablas.size() > 1) {

                DefaultComboBoxModel<String> nuevasOpciones = new DefaultComboBoxModel<>(nombreTablas.toArray(new String[0]));
                botonAgregarTabla.setModel(nuevasOpciones);

            } else {

                panelContenedorFiltros.remove(panelAgregarTabla);
                revalidate();
                repaint();
            }
            botonAgregarTabla.setSelectedIndex(0);
        });
        panelAgregarTabla.add(botonAgregarTabla);
        if (nombreTablas.size() == 1) panelContenedorFiltros.remove(panelAgregarTabla);

        // Agrego el filtro de la tabla seleccionada
        agregarPanelTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);

        revalidate();
        repaint();
    }

    private void agregarPanelTabla(String nombreTabla) {

        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
        panelTabla.setBackground((panelContenedorFiltros.getComponentCount() % 2 == 0)? Color.WHITE : Color.LIGHT_GRAY); 
        JLabel labelNombreTabla = new JLabel(nombreTabla.toUpperCase() + ESPACIO_LABEL);
        labelNombreTabla.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
        labelNombreTabla.setFont(Auxiliar.fuenteNormal.deriveFont(Font.BOLD));
        panelTabla.add(labelNombreTabla);
        panelContenedorFiltros.add(panelTabla);

        ArrayList<String[]> campos = Auxiliar.conexionSQL.obtenerCamposTabla(nombreTabla);

        for (String[] campo : campos) {

            if (!campo[1].equals("Imagen")) {

                JPanel panelFila = new JPanel();
                panelFila.setBackground(null);
                panelFila.setLayout(new BoxLayout(panelFila, BoxLayout.X_AXIS));
                panelFila.setAlignmentX(Component.LEFT_ALIGNMENT);
                panelFila.setPreferredSize(new Dimension(getWidth(),(int)(getHeight()*0.05)));
                panelFila.setMaximumSize(new Dimension(getWidth(),(int)(getHeight()*0.05)));
                panelTabla.add(panelFila);

                JLabel labelNombreCampo = new JLabel(campo[0]);
                labelNombreCampo.setFont(Auxiliar.fuenteNormal);
                panelFila.add(labelNombreCampo);

                switch (campo[1]) {

                    case "Entero":

                        
                        break;

                    case "Decimal":

                        
                        break;

                    case "Texto":

                        
                        break;

                    case "Fecha":

                        
                        break;
                }
            }
        }
    }

    private ArrayList<String> obtenerTablasRelacionadas () {

        ArrayList<String[]> campos = Auxiliar.conexionSQL.obtenerCamposTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
        ArrayList<String> resultado = new ArrayList<>();
        resultado.add(" + Agregar tabla relacionada");
        
        for (String[] campo : campos) {

            if (!campo[2].equals("")) resultado.add(campo[2]);
        }
        return resultado;
    }

    static class ComboBoxRenderer extends DefaultListCellRenderer {
    
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value.equals(" + Agregar tabla relacionada") && index != -1) setText("");
            return component;
        }
    }
}