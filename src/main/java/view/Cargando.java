/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 21 sep. 2023
 * Fecha de modificación: 21 sep. 2023
 * Descripción: Clase para
 *********************************************** */
package view;

import javax.swing.*;
import java.awt.*;

public class Cargando {

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Ejemplo de Pantalla de Carga");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(400, 300);
//            frame.setLayout(new BorderLayout());
//
//            JButton startButton = new JButton("Iniciar Proceso");
//            frame.add(startButton, BorderLayout.CENTER);
//
//            startButton.addActionListener(e -> {
//                // Muestra la pantalla de carga
//                LoadingScreen loadingScreen = new LoadingScreen(frame);
//                loadingScreen.setVisible(true);
//
//                // Ejecuta el proceso en un hilo separado
//                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
//                    @Override
//                    protected Void doInBackground() throws Exception {
//                        // Simula un proceso largo
//                        Thread.sleep(3000);
//                        return null;
//                    }
//
//                    @Override
//                    protected void done() {
//                        // Oculta la pantalla de carga cuando el proceso ha terminado
//                        loadingScreen.dispose();
//                    }
//                };
//
//                worker.execute();
//            });
//
//            frame.setVisible(true);
//        });
//    }
}

class LoadingScreen extends JDialog {

    public LoadingScreen(Frame parent) {
        super(parent, "Cargando...", true);
        setSize(200, 100);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Por favor, espere...");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
