package interfaz.panelPrincipal.subPaneles;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelModificarTabla extends JPanel {
    
    private PanelPrincipal panelPrincipal;
    private Map<String,String> nombresCambiados;
    private Set<String> camposBorrados;
    private ArrayList<JPanel> camposNuevos;
    private ArrayList<JPanel> camposSinModificar;
    private Map<JPanel,String> nombresCamposGuardados;

    @SuppressWarnings("rawtypes")
    public PanelModificarTabla (PanelPrincipal panelPrin) {

        Auxiliar.calcularSize(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2));
        setBackground(Auxiliar.colorGrisFondo);
        setLayout(null);

        panelPrincipal = panelPrin;
        nombresCambiados = new HashMap<>();
        nombresCamposGuardados = new HashMap<>();
        camposBorrados = new HashSet<>();
        camposNuevos = new ArrayList<>();
        camposSinModificar = new ArrayList<>();
        
        // Boton cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setForeground(Auxiliar.colorLetra);
        botonCancelar.setBackground(Auxiliar.colorGrisOscuro);
        botonCancelar.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCancelar.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonCancelar, 0.245, 0.07);
        Auxiliar.calcularLocation(getSize(), botonCancelar, 0.3425, 0.92);
        botonCancelar.addActionListener(accion -> {

            int respuesta = JOptionPane.showConfirmDialog(null, "¿Deseas cancelar la modificación de la tabla?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {

                Auxiliar.habilitacionDeBotones(panelPrincipal, true);
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
            };
		});
        add(botonCancelar);

        // Boton finalizar 
        JButton botonFinalizar = new JButton("Finalizar modificación");
        botonFinalizar.setForeground(Auxiliar.colorLetra);
        botonFinalizar.setBackground(Auxiliar.colorGrisOscuro);
        botonFinalizar.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonFinalizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonFinalizar.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonFinalizar, 0.395, 0.07);
        Auxiliar.calcularLocation(getSize(), botonFinalizar, 0.595, 0.92);
        botonFinalizar.addActionListener(accion -> {

            if (comprobarNombresYClavesForaneas()) {

                for (JPanel panelAComprobar : camposSinModificar) {

                    if (!nombresCamposGuardados.get(panelAComprobar).equals(((JTextField)panelAComprobar.getComponent(1)).getText())) nombresCambiados.put(nombresCamposGuardados.get(panelAComprobar), ((JTextField)panelAComprobar.getComponent(1)).getText());
                }
                Auxiliar.conexionSQL.modificarTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada, nombresCambiados, camposBorrados, camposNuevos);
                panelPrincipal.panelResumenTablas.actualizarPanelResumenTablas();
                Auxiliar.habilitacionDeBotones(panelPrincipal, true);
                panelPrincipal.panelGestionTabla.datosMostrarTabla = Auxiliar.conexionSQL.obtenerTodosLosDatosTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
            }
		});
        add(botonFinalizar);

        // Cabecera con el numero, nombre del campo, tipo de dato, clave foranea y borrar
        JLabel cabeceraNumero = new JLabel("Nº");
        cabeceraNumero.setForeground(Auxiliar.colorLetra);
        cabeceraNumero.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraNumero.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraNumero, 0.06, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraNumero, 0.01, 0.01);
        add(cabeceraNumero);
        
        JLabel cabeceraNombre = new JLabel("Nombre del campo");
        cabeceraNombre.setForeground(Auxiliar.colorLetra);
        cabeceraNombre.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraNombre.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraNombre, 0.355, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraNombre, 0.07, 0.01);
        add(cabeceraNombre);

        JLabel cabeceraTipoDato = new JLabel("Tipo de dato");
        cabeceraTipoDato.setForeground(Auxiliar.colorLetra);
        cabeceraTipoDato.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraTipoDato.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraTipoDato, 0.19, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraTipoDato, 0.425, 0.01);
        add(cabeceraTipoDato);

        JLabel cabeceraClaveForanea = new JLabel("Clave foránea");
        cabeceraClaveForanea.setForeground(Auxiliar.colorLetra);
        cabeceraClaveForanea.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraClaveForanea.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraClaveForanea, 0.25, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraClaveForanea, 0.615, 0.01);
        add(cabeceraClaveForanea);

        JLabel cabeceraBorrar = new JLabel("Borrar");
        cabeceraBorrar.setForeground(Auxiliar.colorLetra);
        cabeceraBorrar.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraBorrar.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraBorrar, 0.11, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraBorrar, 0.865, 0.01);
        add(cabeceraBorrar);

        // Panel para gestionar los campos
        JPanel panelContenedorCampos = new JPanel();
        Auxiliar.calcularSize(getSize(), panelContenedorCampos, 0.98, 0.85);
        panelContenedorCampos.setLayout(new BoxLayout(panelContenedorCampos, BoxLayout.Y_AXIS));
        panelContenedorCampos.setBackground(Auxiliar.colorGrisClaro);

        // Boton crear nuevo campo
        JButton botonCrearCampo = new JButton("Crear campo");
        botonCrearCampo.setForeground(Auxiliar.colorLetra);
        botonCrearCampo.setBackground(Auxiliar.colorGrisOscuro);
        botonCrearCampo.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonCrearCampo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCrearCampo.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonCrearCampo, 0.325, 0.07);
        Auxiliar.calcularLocation(getSize(), botonCrearCampo, 0.01, 0.92);
        botonCrearCampo.addActionListener(accion -> {

            crearCampo(panelContenedorCampos, true);
        });
        add(botonCrearCampo);

        // Agrego los campos que ya existian
        ArrayList<String[]> camposExistentes = Auxiliar.conexionSQL.obtenerCamposTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
        for (String[] campoExistente : camposExistentes) {

            if (!campoExistente[0].equals("id_" + panelPrincipal.panelGestionTabla.nombreTablaSeleccionada)) {

                crearCampo(panelContenedorCampos, false);
                JPanel panelCampo = (JPanel)camposSinModificar.get(camposSinModificar.size()-1);

                ((JTextField)panelCampo.getComponent(1)).setText(campoExistente[0]);
                nombresCamposGuardados.put(panelCampo, ((JTextField)panelCampo.getComponent(1)).getText());

                if (!campoExistente[2].equals("")) {

                    ((JComboBox)panelCampo.getComponent(3)).setSelectedItem("Entero");
                    ((JComboBox)panelCampo.getComponent(5)).setSelectedItem(campoExistente[2]);

                } else ((JComboBox)panelCampo.getComponent(3)).setSelectedItem(campoExistente[1]);
            }
        }
        JScrollPane panelAgregarCampos = new JScrollPane(panelContenedorCampos);
        panelAgregarCampos.setBackground(Auxiliar.colorGrisFondo);
        panelAgregarCampos.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        Auxiliar.ajustarScrollBar(panelAgregarCampos.getVerticalScrollBar());
        Auxiliar.calcularSize(getSize(), panelAgregarCampos, 0.98, 0.85);
        Auxiliar.calcularLocation(getSize(), panelAgregarCampos, 0.01, 0.06);
        panelAgregarCampos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelAgregarCampos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelAgregarCampos.getVerticalScrollBar().setUnitIncrement(20);
        add(panelAgregarCampos);
    }

    @SuppressWarnings("rawtypes")
    private boolean comprobarNombresYClavesForaneas() {

        boolean resultado = camposNuevos.size() + camposSinModificar.size() > 0;
        if (!resultado) JOptionPane.showMessageDialog(null, "La tabla debe contenedor al menos un campo.");

        String nombresVacios = "";
        String nombresRepetidos = "";

        String[] clavesForaneasElegidas = new String[camposSinModificar.size() + camposNuevos.size()];
        String[] nombresCampos = new String[camposSinModificar.size() + camposNuevos.size()];
        for (int c = 0; c < (camposSinModificar.size() + camposNuevos.size()); c++) {

            nombresCampos[c] = (c < camposSinModificar.size())? ((JTextField)camposSinModificar.get(c).getComponent(1)).getText() : ((JTextField)camposNuevos.get(c-camposSinModificar.size()).getComponent(1)).getText();
            clavesForaneasElegidas[c] = (String)((c < camposSinModificar.size())? (((JComboBox)camposSinModificar.get(c).getComponent(5)).getSelectedItem()) : (((JComboBox)camposNuevos.get(c-camposSinModificar.size()).getComponent(5)).getSelectedItem()));
            if (nombresCampos[c] == null || nombresCampos[c].equals("")) {

                nombresVacios += "campo " + (c+1) + ", ";
                resultado = false;
            }
        }
        for (int c1 = 0; c1 < nombresCampos.length; c1++) {
            for (int c2 = c1+1; c2 < nombresCampos.length; c2++) {

                if (nombresCampos[c1] != null && nombresCampos[c2] != null && !nombresCampos[c1].equals("") && nombresCampos[c1].toUpperCase().equals(nombresCampos[c2].toUpperCase())) {

                    nombresRepetidos += "campos " + (c1+1) + " y " + (c2+1) + ", "; 
                    resultado = false;
                }
            }
        }
        if (!resultado && nombresVacios.length() != 0) JOptionPane.showMessageDialog(null, "Los siguientes campos estan vacíos: " + nombresVacios.substring(0, nombresVacios.length()-2) + ".");
        if (!resultado && nombresRepetidos.length() != 0) JOptionPane.showMessageDialog(null, "Los siguientes campos estan repetidos: " + nombresRepetidos.substring(0, nombresRepetidos.length()-2) + ".");
        return resultado;
    }

    private void crearCampo(JPanel panelContenedorCampos, boolean campoNuevo) {

        JPanel panelCampo = new JPanel();
        panelCampo.setLayout(new BoxLayout(panelCampo, BoxLayout.X_AXIS));
        panelCampo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCampo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCampo.setBackground(((camposSinModificar.size() + camposNuevos.size()) % 2 == 0)? Auxiliar.colorGrisClaro : Auxiliar.colorGrisOscuro);

        // Numero del campo
        JLabel numeroDelCampo = new JLabel(((camposSinModificar.size() + camposNuevos.size())+1) + (((camposSinModificar.size() + camposNuevos.size()) > 8)? " " : "   "));
        numeroDelCampo.setForeground(Auxiliar.colorLetra);
        numeroDelCampo.setFont(Auxiliar.fuenteNormal);
        panelCampo.add(numeroDelCampo);

        // Nombre 
        JTextField nombreDelCampo = new JTextField();
        nombreDelCampo.setCaretColor(Auxiliar.colorLetra);
        nombreDelCampo.setForeground(Auxiliar.colorLetra);
        nombreDelCampo.setBackground(Auxiliar.colorGrisOscuro);
        nombreDelCampo.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 1));
        nombreDelCampo.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.355), (int)(getSize().getHeight()*0.05)));
        nombreDelCampo.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.355), (int)(getSize().getHeight()*0.05)));
        nombreDelCampo.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.355), (int)(getSize().getHeight()*0.05)));
        nombreDelCampo.setFont(Auxiliar.fuenteNormal);
        ((PlainDocument)nombreDelCampo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

                if (fb.getDocument().getText(0, fb.getDocument().getLength()).length() - length + (text != null ? text.length() : 0) <= 16) super.replace(fb, offset, length, text, attrs);
            }
        });
        panelCampo.add(nombreDelCampo);

        Component espacioHorizontal1 = Box.createHorizontalStrut(20);
        espacioHorizontal1.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.01), (int)(getSize().getHeight()*0.05)));
        panelCampo.add(espacioHorizontal1);

        // Declaracion de clave foranea
        ArrayList<String> clavesForaneas = Auxiliar.conexionSQL.obtenerNombreTablas();
        clavesForaneas.add(0, "-");
        clavesForaneas.remove("" + panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
        JComboBox<String> comboClavesForaneas = new JComboBox<>(clavesForaneas.toArray(new String[0]));

        // Tipo de dato
        String[] tiposDeDatos = {"Entero", "Decimal", "Texto", "Fecha"};
        JComboBox<String> comboElegirTipoDeDato = new JComboBox<>(tiposDeDatos);
        comboElegirTipoDeDato.setCursor(new Cursor(Cursor.HAND_CURSOR));
        comboElegirTipoDeDato.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.175), (int)(getSize().getHeight()*0.05)));
        comboElegirTipoDeDato.setForeground(Auxiliar.colorLetra);
        comboElegirTipoDeDato.setBackground(Auxiliar.colorGrisOscuro);
        comboElegirTipoDeDato.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 1));
        comboElegirTipoDeDato.setFont(Auxiliar.fuenteNormal);
        comboElegirTipoDeDato.addActionListener(accion2 -> {

            if (campoNuevo) comboClavesForaneas.setEnabled(((String)comboElegirTipoDeDato.getSelectedItem()).equals("Entero"));
        });
        comboElegirTipoDeDato.setEnabled(campoNuevo);
        panelCampo.add(comboElegirTipoDeDato);

        Component espacioHorizontal2 = Box.createHorizontalStrut(20);
        espacioHorizontal2.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.01), (int)(getSize().getHeight()*0.05)));
        panelCampo.add(espacioHorizontal2);
        
        // Configuracion de clave foranea
        comboClavesForaneas.setForeground(Auxiliar.colorLetra);
        comboClavesForaneas.setBackground(Auxiliar.colorGrisOscuro);
        comboClavesForaneas.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 1));
        comboClavesForaneas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        comboClavesForaneas.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.3), (int)(getSize().getHeight()*0.05)));
        comboClavesForaneas.setFont(Auxiliar.fuenteNormal);
        comboClavesForaneas.setEnabled(campoNuevo);
        panelCampo.add(comboClavesForaneas);

        // Eliminar
        JButton botonEliminarCampo = new JButton();
        botonEliminarCampo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonEliminarCampo.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/removeBin.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()* 0.04), (int)(Auxiliar.dimensionVentana.getHeight()* 0.04), Image.SCALE_SMOOTH)));
        botonEliminarCampo.setBorderPainted(false);
        botonEliminarCampo.setContentAreaFilled(false);
        botonEliminarCampo.addActionListener(accion2 -> {

            panelContenedorCampos.remove(panelCampo);
            for (int c = 0; c < panelContenedorCampos.getComponentCount(); c++) {

                JPanel panelCampoAux = (JPanel)panelContenedorCampos.getComponent(c);
                ((JLabel)panelCampoAux.getComponent(0)).setText((c+1) + (((c+1) > 9)? " " : "   "));
                panelCampoAux.setBackground(((c) % 2 == 0)? Auxiliar.colorGrisClaro : Auxiliar.colorGrisOscuro);
            }
            if (!campoNuevo) {

                camposBorrados.add(nombreDelCampo.getText());
                camposSinModificar.remove(panelCampo);

            } else camposNuevos.remove(panelCampo);

            revalidate();
            repaint();
        });
        panelCampo.add(botonEliminarCampo);

        if (campoNuevo) camposNuevos.add(panelCampo);
        if (!campoNuevo) camposSinModificar.add(panelCampo);
        panelContenedorCampos.add(panelCampo);
        revalidate();
        repaint();
    }
}