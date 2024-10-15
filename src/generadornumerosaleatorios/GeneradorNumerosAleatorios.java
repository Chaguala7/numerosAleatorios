package generadornumerosaleatorios;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatSolarizedLightIJTheme;


public class GeneradorNumerosAleatorios {

    public static void main(String[] args) {
        try {
            FlatSolarizedLightIJTheme.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        Ventana x = new Ventana();
        x.setVisible(true);
    }

}
