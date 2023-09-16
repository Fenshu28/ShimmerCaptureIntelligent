/** **********************************************
 * Autores: Cristopher Alexis Zarate Valencia  *
 * Fecha de creación: 9 sep. 2023                *
 * Fecha de modificación: 9 sep. 2023            *
 * Descripción: Clase para hacer un hilo para actualizar los componentes del
 * frame.
 *********************************************** */
package util;

import java.awt.Color;
import view.MainFrame;

public final class UpdateComponentsThread implements Runnable {

    private MainFrame main_Frame;
    private boolean active;
    private String connStatus = new String();

    public UpdateComponentsThread(MainFrame main_Frame) {
        this.main_Frame = main_Frame;
        setActive(true);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        while (isActive()) {
            updateLabelConn();
            updateBarBattery();
        }
    }

    /**
     * Método para actualizar el label del estado de la conexión, determina que
     * color modificar y valor del texto.
     */
    private void updateLabelConn() {
        connStatus = main_Frame.getCon().getStatus();

        if (connStatus.contains("Conectado")) {
            main_Frame.getLbEstado().setForeground(Color.GREEN);
        } else if (connStatus.contains("Conectando")) {
            main_Frame.getLbEstado().setForeground(Color.orange);
        } else {
            main_Frame.getLbEstado().setForeground(Color.red);
        }

        main_Frame.getLbEstado().setText(main_Frame.getCon().getStatus());
    }

    public void updateBarBattery() {
        if (connStatus.contains("Conectado")) {
            main_Frame.getBarBattery().setValue((int) main_Frame.getCon().
                    getShimmerDevice().getBatteryLevel());
            
        }

    }
}
