/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 22 sep. 2023
 * Fecha de modificación: 22 sep. 2023
 * Descripción: Clase para
 *********************************************** */
package controller;

import entity.Timer;

public class TimerController {

    private Timer timer;

    public TimerController() {
        timer = new Timer();
    }

    public void tick() {
        timerAlgorith();
    }

    private void timerAlgorith() {
        if (timer.getS() == 60) {
            timer.setS(0);
            timer.setM(timer.getM() + 1);
        } else if (timer.getM() == 60) {
            timer.setM(0);
            timer.setH(timer.getH() + 1);
        } else {
            timer.setS(timer.getS() + 1);
        }
    }

    public String getTime() {
        return String.format("%02d", timer.getH()) + ":"
                + String.format("%02d", timer.getM()) + ":"
                + String.format("%02d", timer.getS());
    }
}
