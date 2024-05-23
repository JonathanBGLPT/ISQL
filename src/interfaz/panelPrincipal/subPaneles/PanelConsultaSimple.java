package interfaz.panelPrincipal.subPaneles;

import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelConsultaSimple extends JPanel {
    
    private static final String ESPACIO_LABEL = "                                                                                                                                ";
    private static final String[] OPCIONES_ENTEROS_REALES_FECHAS = {"-", "=", "<", ">", "<=", ">=", "rango"};
    private static final String[] OPCIONES_TEXTOS = {"-", "igual", "contenga", "empiece", "acabe en"};

    private PanelPrincipal panelPrincipal;
    private JPanel panelContenedorFiltros;
    private ArrayList<String> columnasEliminadas;

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
        columnasEliminadas = new ArrayList<>();

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

           ArrayList<String[]> filtros = obtenerFiltroAplicar();
            if (filtros != null) {

                panelPrincipal.panelConsultas.columnasEliminadas = columnasEliminadas;
                panelPrincipal.panelGestionTabla.datosMostrarTabla = Auxiliar.conexionSQL.obtenerConsultaFiltrada(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada, filtros);
                panelPrincipal.panelConsultas.elegirPanelConsulta(2);
            }
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

        // Agrego los filtros de la tabla seleccionada
        JLabel labelNombreTabla = new JLabel(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada.toUpperCase() + ESPACIO_LABEL);
        labelNombreTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        labelNombreTabla.setFont(Auxiliar.fuenteNormal.deriveFont(Font.BOLD));
        panelContenedorFiltros.add(labelNombreTabla);

        ArrayList<String[]> campos = Auxiliar.conexionSQL.obtenerCamposTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);

        for (String[] campo : campos) {

            JPanel panelFila = new JPanel();
            panelFila.setBackground(null);
            panelFila.setLayout(new BoxLayout(panelFila, BoxLayout.X_AXIS));
            panelFila.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelFila.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 0));
            panelFila.setPreferredSize(new Dimension(getWidth(),(int)(getHeight()*0.05)));
            panelFila.setMaximumSize(new Dimension(getWidth(),(int)(getHeight()*0.05)));
            panelContenedorFiltros.add(panelFila);

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
            panelFila.add(campo[0].equals("id_" + panelPrincipal.panelGestionTabla.nombreTablaSeleccionada)? Box.createHorizontalStrut((int)(getWidth()*0.04)) : seleccionarFila);
            panelFila.add(Box.createHorizontalStrut(8));
            
            // Nombre del campo
            JLabel labelNombreCampo = new JLabel(campo[0]);
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
        revalidate();
        repaint();
    }

    @SuppressWarnings("rawtypes")
    private ArrayList<String[]> obtenerFiltroAplicar() {

        ArrayList<String[]> resultado = new ArrayList<>();

        boolean resultadoValido = true;
        ArrayList<String[]> camposTabla = Auxiliar.conexionSQL.obtenerCamposTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);

        for (int f = 1; f < panelContenedorFiltros.getComponentCount(); f++) {

            JPanel panelFila = (JPanel)panelContenedorFiltros.getComponent(f);
            if (((JCheckBox)panelFila.getComponent(0)).isSelected()) {

                String[] filtroFila = new String[5];
                resultado.add(filtroFila);
                filtroFila[0] = ((JLabel)panelFila.getComponent(3)).getText();
                filtroFila[1] = (String)((JComboBox)panelFila.getComponent(4)).getSelectedItem();
                filtroFila[2] = camposTabla.get(f-1)[1];
                if (!filtroFila[1].equals("-")) {

                    filtroFila[3] = ((JTextField)(panelFila.getComponent(6))).getText();
                    if (filtroFila[1].equals("rango")) filtroFila[4] = ((JTextField)(panelFila.getComponent(7))).getText();
                    if (!camposTabla.get(f-1)[1].equals("Texto")) {

                        switch (camposTabla.get(f-1)[1]) {

                            case "Entero":

                                try {

                                    Integer.parseInt(filtroFila[3]);
                                    if (filtroFila[1].equals("rango")) Integer.parseInt(filtroFila[4]);

                                } catch (NumberFormatException e) {

                                    resultadoValido = false;
                                    JOptionPane.showMessageDialog(null, "El filtro '" + filtroFila[0] + "' no es válido.");
                                }
                                break;

                            case "Decimal":

                                try {

                                    Double.parseDouble(filtroFila[3].replace(",", "."));
                                    if (filtroFila[1].equals("rango")) Double.parseDouble(filtroFila[4].replace(",", "."));

                                } catch (NumberFormatException e) {

                                    resultadoValido = false;
                                    JOptionPane.showMessageDialog(null, "El filtro '" + filtroFila[0] + "' no es válido.");
                                }
                                break;

                            case "Fecha":
                                
                                boolean fecha1Valida = Auxiliar.comprobarFechaNumeros(filtroFila[3]) && comprobarFechaValida(filtroFila[3]);
                                boolean fecha2Valida = !filtroFila[1].equals("rango") || (Auxiliar.comprobarFechaNumeros(filtroFila[4]) && comprobarFechaValida(filtroFila[4]));
                                if (!fecha1Valida || !fecha2Valida) {

                                    resultadoValido = false;
                                    JOptionPane.showMessageDialog(null, "El filtro '" + filtroFila[0] + "' no es válido.");

                                }

                                filtroFila[3] = convertirFechaAFormatoCorrecto(filtroFila[3]);
                                if (filtroFila[1].equals("rango")) filtroFila[4] = convertirFechaAFormatoCorrecto(filtroFila[4]);

                                break;

                        }
                    }
                }
            } else columnasEliminadas.add(((JLabel)panelFila.getComponent(3)).getText());
        }
        return (resultadoValido)? resultado : null;
    }

    private String convertirFechaAFormatoCorrecto (String fecha) {

        String resultado = "";

        SimpleDateFormat formato;
        if (fecha.contains("/")) {

            if (fecha.indexOf('/') == 4) {

                formato = new SimpleDateFormat("yyyy/MM/dd");

            } else formato = new SimpleDateFormat("dd/MM/yyyy");

        } else if (fecha.contains("-")) {

            if (fecha.indexOf('-') == 4) {

                formato = new SimpleDateFormat("yyyy-MM-dd");

            } else formato = new SimpleDateFormat("dd-MM-yyyy");

        } else return "";

        try {

            resultado = "" + formato.parse(fecha).getTime();

        } catch (ParseException e) { return ""; }

        return resultado;
    }

    private boolean comprobarFechaValida(String fecha) {

        SimpleDateFormat formato;
        if (fecha.contains("/")) {

            if (fecha.indexOf('/') == 4) {

                formato = new SimpleDateFormat("yyyy/MM/dd");

            } else formato = new SimpleDateFormat("dd/MM/yyyy");

        } else if (fecha.contains("-")) {

            if (fecha.indexOf('-') == 4) {

                formato = new SimpleDateFormat("yyyy-MM-dd");

            } else formato = new SimpleDateFormat("dd-MM-yyyy");

        } else return false;

        try {

            new Date(formato.parse(fecha).getTime());

        } catch (ParseException e) { return false; }

        return true;
    }
}