/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 13/09/2023
 * Fecha de modificación: 13/09/2023
 * Descripción: Clase para represenar a un archivo CSV y guardar los datos.
 *********************************************** */
package entity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.joda.time.format.DateTimeFormat;

public class FileCSV {

    private String ruta;
    private String nombre_Archivo;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    /**
     * Crea la instancia del objeto para controlar en archivo CSV, requiere 2
     * parametros para poder crearlo.
     *
     * @param ruta Lugar donde se guardará el archivo.
     * @param nombre_Archivo Nombre del archivo.
     */
    public FileCSV(String ruta, String nombre_Archivo) {
        this.ruta = ruta;
        this.nombre_Archivo = nombre_Archivo;
    }

    /**
     * Inicia el archivo para poder insertar datos.
     */
    public void openFile() {
        // Obtener la fecha y hora actual
        Date fechaHoraActual = new Date();

        // Crear un objeto SimpleDateFormat con el formato deseado
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");

        // Formatear la fecha y hora actual
        String fechaHoraFormateada = formato.format(fechaHoraActual);
        try {
            fileWriter = new FileWriter(ruta + "\\" + nombre_Archivo
                    + "_" + fechaHoraFormateada + ".csv");
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Error al crear el archivo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea las columnas para los datos del archivo.
     *
     * @param columns Lista del nombre de las columnas.
     */
    public void setColumns(List<String> columns) {
        try {
            for (String column : columns) {
                bufferedWriter.write(column + ",");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Error al asignar columna",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Inserta una tupla de datos.
     *
     * @param data
     */
    public void setData(List<String> data) {
        try {
            bufferedWriter.newLine();

            for (String d : data) {
                bufferedWriter.write(d + ",");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Error guardar datos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cierra el archivo.
     */
    public void closeFile() {
        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Error al guardar el archivo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
