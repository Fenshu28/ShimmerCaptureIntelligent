/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 26 sep. 2023
 * Descripción: Clase para crear un hilo que actualice los datos de los sensores
 * en el frame.
 *********************************************** */
package threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import resource.StatusConection;
import view.MainFrame;

public class UpdateLabelDataThread implements Runnable {
    
    private final MainFrame main_Frame;
    private boolean active;
        
    public UpdateLabelDataThread(MainFrame main_Frame) {
        this.main_Frame = main_Frame;
        this.active = true;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public void run() {
        while (active) {
            try {
                if (main_Frame.getCon().getStatus_Stream().contains(
                        StatusConection.Transmitiendo.toString())) {
                    updateDataLabel();
                    System.out.println("tick");
                }
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(UpdateLabelDataThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    private void updateDataLabel() {
        try {
            System.out.println(main_Frame.getCon().
                    getShimmerDevice().getData().get(1) + " mSimens");
//            main_Frame.getLbGsrCond().setText(main_Frame.getCon().
//                    getShimmerDevice().getData().get(1) + " mSimens");
//            main_Frame.getLbGsrRes().setText(main_Frame.getCon().
//                    getShimmerDevice().getData().get(3) + " KOhms");
//            main_Frame.getLbHR().setText(main_Frame.getCon().
//                    getShimmerDevice().getData().get(5) + " Beats/min.");
//            main_Frame.getLbPpg().setText(main_Frame.getCon().
//                    getShimmerDevice().getData().get(6) + " mVolts"); // Se debe cambiar por 7.
        } catch (Exception e) {
            System.out.println("nopi");
        }
    }
}
