/** **********************************************
 * Autores: Cristopher Alexis Zarate Valencia  *
 * Fecha de creación: 9 sep. 2023                *
 * Fecha de modificación: 9 sep. 2023            *
 * Descripción: Clase para hacer un hilo para actualizar los componentes del
 * frame.
 *********************************************** */
package util;

import view.MainFrame;

public final class UpdateComponentsThread implements Runnable {

    private MainFrame main_Frame;
    private boolean active;

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
            main_Frame.getLbEstado().setText(main_Frame.getCon().getStatus());
        }
    }
}
