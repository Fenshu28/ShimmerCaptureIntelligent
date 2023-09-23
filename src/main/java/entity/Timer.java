/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 22 sep. 2023
 * Fecha de modificación: 22 sep. 2023
 * Descripción: Clase para crear un timer.
 *********************************************** */
package entity;

public class Timer {

    private int s, m, h;

    public Timer() {
        s = 0;
        m = 0;
        h = 0;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

}
