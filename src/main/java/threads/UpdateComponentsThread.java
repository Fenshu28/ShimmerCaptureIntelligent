/** **********************************************
 * Autores: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 9 sep. 2023
 * Fecha de modificación: 18 sep. 2023
 * Descripción: Clase para hacer un hilo para actualizar los componentes del
 * frame.
 *********************************************** */
package threads;

import java.awt.Color;
import resource.StatusConection;
import view.MainFrame;

public final class UpdateComponentsThread implements Runnable {

    private final MainFrame main_Frame;
    private boolean active;
    private String lastConnStatus = new String();
    private String connStatus = new String();
    private boolean lastStatusRecord = false;
    private boolean statusRecord = false;
    private int batteryLevel;
    private int lastBatteryLevel = 101;

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
            main_Frame.getCon().getStatus_Stream();
            statusRecord = main_Frame.getCon().isOnRec();

            // Procesos que ejecutará solo si el estado cambia.
            if (!lastConnStatus.equals(connStatus)) {
                updateLabelConn();
                updateButtonsConn();
            }
            if (lastStatusRecord != statusRecord) {
                updateButtonsRec();
            }

            // Procesos si el estado es uno especifico
            if (!statusRecord) {
                updateFileProp(true);
            } else {

                updateFileProp(false);
            }

//            if (connStatusStream.equals(StatusConection.Transmitiendo.toString())) {
            updateDataLabel();
//            }
            if (connStatus.contains(StatusConection.Conectado.toString())) {
//                    updateBarBattery();
                updateLabelStatusBattery();
            }
            lastConnStatus = connStatus;
            lastStatusRecord = statusRecord;
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
        batteryLevel = (int) main_Frame.getCon().
                getShimmerDevice().getBatteryLevel();

        if (lastBatteryLevel < batteryLevel) {
            main_Frame.getBarBattery().setValue(lastBatteryLevel);
        } else {
            main_Frame.getBarBattery().setValue(lastBatteryLevel);
            lastBatteryLevel = batteryLevel;
        }

    }

    public void updateLabelStatusBattery() {
        String batSta = main_Frame.getCon().getShimmerDevice().getBatterystatus();
        if (batSta.contains("UNKNOWN")) {
            main_Frame.getLbEstadoBatt().setForeground(Color.gray);
        } else if (batSta.contains("LOW")) {
            main_Frame.getLbEstadoBatt().setForeground(Color.red);
        } else if (batSta.contains("MEDIUM")) {
            main_Frame.getLbEstadoBatt().setForeground(Color.orange);
        } else if (batSta.contains("HIGH")) {
            main_Frame.getLbEstadoBatt().setForeground(Color.green);
        }
        main_Frame.getLbEstadoBatt().setText(batSta);
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

    public void updateButtonsRec() {
        if (statusRecord) {
            setActiveButtonsRec(false);
        } else {
            setActiveButtonsRec(true);
        }
    }

    private void setActiveButtonsConnection(boolean flag) {
        // Botones de conexión.
        main_Frame.getBtnConect().setEnabled(!flag);
        main_Frame.getBtnDisconect().setEnabled(flag);
    }

    private void setActiveButtonsRec(boolean flag) {
        main_Frame.getBtnPlay().setVisible(flag);

        main_Frame.getBtnPause().setVisible(!flag);
        main_Frame.getBtnStop().setVisible(!flag);
    }

    private void updateDataLabel() {
        try {
            main_Frame.getLbGsrCond().setText(main_Frame.getCon().
                    getShimmerDevice().getData().get(1) + " mSimens");
            main_Frame.getLbGsrRes().setText(main_Frame.getCon().
                    getShimmerDevice().getData().get(3) + " KOhms");
            main_Frame.getLbHR().setText(main_Frame.getCon().
                    getShimmerDevice().getData().get(5) + " Beats/min.");
            main_Frame.getLbPpg().setText(main_Frame.getCon().
                    getShimmerDevice().getData().get(6) + " mVolts"); // Se debe cambiar por 7.
        } catch (Exception e) {
            System.out.println("nopi");
        }
    }

    private void updateFileProp(boolean flag) {
        if (main_Frame.getTxtNameFile().getText().isEmpty()) {
            main_Frame.getBtnChoosePathFile().setEnabled(!flag);
        } else {
            main_Frame.getBtnChoosePathFile().setEnabled(flag);
        }
        main_Frame.getTxtNameFile().setEnabled(flag);
    }
}
