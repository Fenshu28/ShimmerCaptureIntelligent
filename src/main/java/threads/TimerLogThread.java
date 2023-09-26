/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 26 sep. 2023
 * Fecha de modificación: 26 sep. 2023
 * Descripción: Clase para un hilo que actualice un timer del tiempo de guardado.
 *********************************************** */
package threads;

import controller.TimerController;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.MainFrame;

public class TimerLogThread implements Runnable {

    private TimerController timController;
    private MainFrame main_Frame;
    private boolean active = false;

    public TimerLogThread(MainFrame main_Frame) {
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
                if (main_Frame.getCon().isOnRec()) {
                    timController.tick();
                    main_Frame.getLbTimerRec().setText(timController.getTime());
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(TimerConectThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
