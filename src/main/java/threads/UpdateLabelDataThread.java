/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 26 sep. 2023
 * Descripción: Clase para crear un hilo que actualice los datos de los sensores
 * en el frame.
 *********************************************** */
package threads;

import view.MainFrame;

public class UpdateLabelDataThread {

    private MainFrame main_Frame;
    private boolean active;
    private String lastConnStatus = new String();
    private String connStatus = new String();
    private String lastStatusRecord = new String();
    private String statusRecord = new String();

    public UpdateLabelDataThread(MainFrame main_Frame) {
        this.main_Frame = main_Frame;
        this.active = true;
    }
    
    
}
