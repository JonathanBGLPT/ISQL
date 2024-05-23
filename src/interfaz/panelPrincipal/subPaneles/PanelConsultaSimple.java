package interfaz.panelPrincipal.subPaneles;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelConsultaSimple extends JPanel {
    
    private static final String ESPACIO_LABEL = "                                                                                                                                ";
    private static final String[] OPCIONES_ENTEROS_REALES_FECHAS = {"-", "=", "<", "<=", ">=", "rango"};
    private static final String[] OPCIONES_TEXTOS = {"-", "igual", "contenga", "empiece", "acabe en"};

    private PanelPrincipal panelPrincipal;
    private JPanel panelContenedorFiltros;

    private ImageIcon checkSI;
    private ImageIcon checkNO;

    public PanelConsultaSimple (PanelPrincipal panelPrin) {

        Auxiliar.calcularSize(panelPrin.panelConsultas.panelDeConsultas.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelConsultas.panelDeConsultas.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
        setLayout(null);

        panelPrincipal = panelPrin;
        checkSI = new ImageIcon(new ImageIcon(getClass().getResource("/checkSI.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.04), (int)(Auxiliar.dimensionVentana.getHeight()*0.04), Image.SCALE_SMOOTH));
        checkNO = new ImageIcon(new ImageIcon(getClass().getResource("/checkNO.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.04), (int)(Auxiliar.dimensionVentana.getHeight()*0.04), Image.SCALE_SMOOTH));

        // Boton para cancelar la consulta
        JButton botonCancelarConsulta = new JButton("Cancelar");
        botonCancelarConsulta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCancelarConsulta.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonCancelarConsulta, 0.485, 0.07);
        Auxiliar.calcularLocation(getSize(), botonCancelarConsulta, 0.01, 0.92);
        botonCancelarConsulta.addActionListener(accion -> {

            panelPrincipal.panelConsultas.elegirPanelConsulta(0);
        });
        add(botonCancelarConsulta);

        // Boton para realizar la consulta
        JButton botonRealizarConsulta = new JButton("Consultar");
        botonRealizarConsulta.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        botonAgregarTabla.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
                panelFila.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 0));
                panelFila.setPreferredSize(new Dimension(getWidth(),(int)(getHeight()*0.05)));
                panelFila.setMaximumSize(new Dimension(getWidth(),(int)(getHeight()*0.05)));
                panelTabla.add(panelFila);

                // Check para decidir si se muestra el campo o no
                JCheckBox seleccionarFilaEstaSeleccionado = new JCheckBox();
                seleccionarFilaEstaSeleccionado.setSelected(true);
                seleccionarFilaEstaSeleccionado.setVisible(false);
                panelFila.add(seleccionarFilaEstaSeleccionado);
                JButton seleccionarFila = new JButton();
                seleccionarFila.setCursor(new Cursor(Cursor.HAND_CURSOR));
                seleccionarFila.setIcon(checkSI);
                seleccionarFila.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.04), (int)(getSize().getWidth()*0.04)));
                seleccionarFila.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.04), (int)(getSize().getWidth()*0.04)));
                seleccionarFila.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.04), (int)(getSize().getWidth()*0.04)));
                seleccionarFila.addActionListener(accion -> {
    
                    seleccionarFilaEstaSeleccionado.setSelected(!seleccionarFilaEstaSeleccionado.isSelected());
                    seleccionarFila.setIcon((seleccionarFilaEstaSeleccionado.isSelected())? checkSI : checkNO);
                });
                panelFila.add(seleccionarFila);
                panelFila.add(Box.createHorizontalStrut(8));
                
                // Nombre del campo
                JLabel labelNombreCampo = new JLabel("<html>&#8203;" + campo[0] + "</html>");
                labelNombreCampo.setPreferredSize(new Dimension((int)(getWidth()*0.35), (int)(getHeight()*0.05)));
                labelNombreCampo.setMinimumSize(new Dimension((int)(getWidth()*0.35), (int)(getHeight()*0.05)));
                labelNombreCampo.setMaximumSize(new Dimension((int)(getWidth()*0.35), (int)(getHeight()*0.05)));
                labelNombreCampo.setFont(Auxiliar.fuenteNormal);
                panelFila.add(labelNombreCampo);

                // Tipo de filtro a aplicar
                JComboBox<String> comboTipoFiltro = new JComboBox<>(campo[1].equals("Texto")? OPCIONES_TEXTOS : OPCIONES_ENTEROS_REALES_FECHAS);
                comboTipoFiltro.setCursor(new Cursor(Cursor.HAND_CURSOR));
                comboTipoFiltro.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.15), (int)(getSize().getHeight()*0.05)));
                comboTipoFiltro.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.15), (int)(getSize().getHeight()*0.05)));
                comboTipoFiltro.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.15), (int)(getSize().getHeight()*0.05)));
                comboTipoFiltro.setFont(Auxiliar.fuenteNormal);
                panelFila.add(comboTipoFiltro);
                panelFila.add(Box.createHorizontalStrut(8));

                if (campo[1].equals("Texto")) {

                    comboTipoFiltro.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.225), (int)(getSize().getHeight()*0.05)));

                    // Texto a filtrar
                    JTextField valorDelFiltro = new JTextField();
                    valorDelFiltro.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.275), (int)(getSize().getHeight()*0.05)));
                    valorDelFiltro.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.275), (int)(getSize().getHeight()*0.05)));
                    valorDelFiltro.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.275), (int)(getSize().getHeight()*0.05)));
                    valorDelFiltro.setFont(Auxiliar.fuenteNormal);
                    panelFila.add(valorDelFiltro);

                } else {

                    // Primer valor a filtrar
                    JTextField valorDelFiltro1 = new JTextField();
                    valorDelFiltro1.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.35), (int)(getSize().getHeight()*0.05)));
                    valorDelFiltro1.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.35), (int)(getSize().getHeight()*0.05)));
                    valorDelFiltro1.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.35), (int)(getSize().getHeight()*0.05)));
                    valorDelFiltro1.setFont(Auxiliar.fuenteNormal);
                    panelFila.add(valorDelFiltro1);

                    // Segundo valor a filtrar si el tipo de filtro es 'rango'
                    JTextField valorDelFiltro2 = new JTextField();
                    valorDelFiltro2.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.175), (int)(getSize().getHeight()*0.05)));
                    valorDelFiltro2.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.175), (int)(getSize().getHeight()*0.05)));
                    valorDelFiltro2.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.175), (int)(getSize().getHeight()*0.05)));
                    valorDelFiltro2.setFont(Auxiliar.fuenteNormal);

                    comboTipoFiltro.addActionListener(accion -> {

                        if (((String)comboTipoFiltro.getSelectedItem()).equals("rango")) {

                            valorDelFiltro1.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.175), (int)(getSize().getHeight()*0.05)));
                            valorDelFiltro1.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.175), (int)(getSize().getHeight()*0.05)));
                            valorDelFiltro1.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.175), (int)(getSize().getHeight()*0.05)));
                            panelFila.add(valorDelFiltro2);

                        } else {

                            valorDelFiltro1.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.35), (int)(getSize().getHeight()*0.05)));
                            valorDelFiltro1.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.35), (int)(getSize().getHeight()*0.05)));
                            valorDelFiltro1.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.35), (int)(getSize().getHeight()*0.05)));
                            panelFila.remove(valorDelFiltro2);
                        }
                        revalidate();
                        repaint();
                    });
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

    private Map<String,String[]> obtenerFiltroAplicar() {

        Map<String,String[]> resultado = new HashMap<>();



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