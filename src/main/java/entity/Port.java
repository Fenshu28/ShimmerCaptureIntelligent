/**
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha: 08/09/2023.
 */
package entity;

public class Port {
    private String nombre;
    private String dispositivo;
    private String descripción;

    public Port(String nombre, String descripción) {
        this.nombre = nombre;
        this.descripción = descripción;
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

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }
}
