/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 27 sep. 2023
 * Descripción: Clase principal.
 *********************************************** */

package mainPack;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import javax.swing.UIManager;
import view.MainFrame;


public class Main {
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
