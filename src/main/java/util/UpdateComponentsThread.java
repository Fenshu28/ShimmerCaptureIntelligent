/** **********************************************
 * Autores: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 9 sep. 2023
 * Fecha de modificación: 18 sep. 2023
 * Descripción: Clase para hacer un hilo para actualizar los componentes del
 * frame.
 *********************************************** */
package util;

import java.awt.Color;
import resource.StatusConection;
import view.MainFrame;

public final class UpdateComponentsThread implements Runnable {

    private MainFrame main_Frame;
    private boolean active;
    private String lastConnStatus = new String();
    private String connStatus = new String();
    private String lastConnStatusStream = new String();
    private String connStatusStream = new String();

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
            connStatus = main_Frame.getCon().getStatus();
            connStatusStream = main_Frame.getCon().getStatus_Stream();

            // Procesos que se ejecutará siempre.
            updateBarBattery();

            // Procesos que ejecutará solo si el estado cambia.
            if (!lastConnStatus.equals(connStatus)) {
                updateLabelConn();
                updateButtonsConn();
            } else if (!lastConnStatusStream.equals(connStatusStream)) {
                updateButtonsStream();
            }

            lastConnStatus = connStatus;
            lastConnStatusStream = connStatusStream;
        }
    }

    /**
     * Método para actualizar el label del estado de la conexión, determina que
     * color modificar y valor del texto.
     */
    private void updateLabelConn() {
        if (connStatus.contains(StatusConection.Conectado.toString())) { // Si esta conectado
            main_Frame.getLbEstado().setForeground(Color.GREEN);
        } else if (connStatus.contains(StatusConection.Conectando.toString())) { // Sí esta conectando
            main_Frame.getLbEstado().setForeground(Color.orange);
        } else {
            main_Frame.getLbEstado().setForeground(Color.red);
        }

        main_Frame.getLbEstado().setText(main_Frame.getCon().getStatus());
    }

    /**
     * Actualiza la barra de bateria en el frame.
     */
    public void updateBarBattery() {
        if (connStatus.contains(StatusConection.Conectado.toString())) {
            main_Frame.getBarBattery().setValue((int) main_Frame.getCon().
                    getShimmerDevice().getBatteryLevel());
        }
    }

    /**
     * Actualiza los botones del frame.
     */
    public void updateButtonsConn() {
        if (connStatus.contains(StatusConection.Conectado.toString())) { // Si esta conectado.
            setActiveButtonsConnection(true);
        } else if (connStatus.contains(
                StatusConection.Desconectado.toString())) { // Si esta desconectado.
            setActiveButtonsConnection(false);
        }
    }

    public void updateButtonsStream() {
        if (connStatusStream.contains(
                StatusConection.Transmitiendo.toString())) { // si esta transmitiendo.
            setActiveButtonsStream(false);
        } else if (connStatusStream.contains(
                StatusConection.Parado.toString())
                || connStatusStream.contains(
                        StatusConection.Pausado.toString())) { // si esta parado.
            setActiveButtonsStream(true);
        } else if (connStatusStream.contains(
                StatusConection.Inicializado.toString())) { // si se inicializo correctamente.
            setActiveButtonsStream(true);
        }
    }

    private void setActiveButtonsConnection(boolean flag) {
        // Botones de conexión.
        main_Frame.getBtnConect().setEnabled(!flag);
        main_Frame.getBtnDisconect().setEnabled(flag);
    }

    private void setActiveButtonsStream(boolean flag) {
        // Botones de transmisión
//        main_Frame.getBtnPlay().setEnabled(flag);
        main_Frame.getBtnPlay().setVisible(flag);

        main_Frame.getBtnPause().setVisible(!flag);
        main_Frame.getBtnStop().setVisible(!flag);

    }
}
