package interfaz;

import java.io.File;
import javax.swing.*;

public class PanelGestionBBDD extends JPanel {

    private File carpetaBBDD;

    public PanelGestionBBDD () {

        this.setSize(Auxiliar.dimensionVentana);
        this.setBackground(Auxiliar.colorAzulPalido);

        carpetaBBDD = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "ISQL");
        carpetaBBDD.mkdirs();
    }
}
