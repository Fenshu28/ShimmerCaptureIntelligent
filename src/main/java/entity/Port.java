/**
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha: 08/09/2023.
 */
package entity;

public class Port {
    private String nombre;
    private String dispositivo;
    private String descripci�n;

    public Port(String nombre, String descripci�n) {
        this.nombre = nombre;
        this.descripci�n = descripci�n;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getDescripci�n() {
        return descripci�n;
    }

    public void setDescripci�n(String descripci�n) {
        this.descripci�n = descripci�n;
    }
}
