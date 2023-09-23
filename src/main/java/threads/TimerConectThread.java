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
import view.MainFrame;

public class TimerConectThread implements Runnable {

    TimerController timController;
    MainFrame main_Frame;
    
    public TimerConectThread(MainFrame main_Frame) {
        this.main_Frame = main_Frame;
        timController = new TimerController();
    }    
    
    @Override
    public void run() {
        while (true) {            
            try {
                timController.tick();
                main_Frame.getLbTimerConect().setText(timController.getTime());
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TimerConectThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
