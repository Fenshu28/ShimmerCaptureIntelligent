/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 22 sep. 2023
 * Fecha de modificación: 22 sep. 2023
 * Descripción: Clase para crear un hilo que ejecute timer para el control del
 * tiempo de conectado.
 *********************************************** */
package threads;

import controller.TimerController;
import java.util.logging.Level;
import java.util.logging.Logger;
import resource.StatusConection;
import view.MainFrame;

public class TimerConectThread implements Runnable {

    private TimerController timController;
    private MainFrame main_Frame;
    private boolean active = false;

    public TimerConectThread(MainFrame main_Frame) {
        this.main_Frame = main_Frame;
        timController = new TimerController();
        active = true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        while (active) {
            try {
                if (main_Frame.getCon().getStatus_Stream().equals(
                        StatusConection.Transmitiendo.toString())) {
                    timController.tick();
                    main_Frame.getLbTimerConect().setText(timController.getTime());
                }
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TimerConectThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
