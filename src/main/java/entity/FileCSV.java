/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creaci�n: 13/09/2023
 * Fecha de modificaci�n: 13/09/2023
 * Descripci�n: Clase para represenar a un archivo CSV y guardar dentro de �l.
 *********************************************** */
package entity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import javax.swing.JOptionPane;

public class FileCSV {

    private String ruta;
    private String nombre_Archivo;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public FileCSV(String ruta, String nombre_Archivo) {
        this.ruta = ruta;
        this.nombre_Archivo = nombre_Archivo;
    }

    public void openFile() {
        try {
            fileWriter = new FileWriter(ruta + nombre_Archivo + ".csv");
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    e.getMessage(), "Error al crear el archivo", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setColumns(List<String> columns){
        try {
            bufferedWriter.write("Columna1,Columna2,Columna3");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    e.getMessage(),"Error al asignar columna", 
                    JOptionPane.ERROR_MESSAGE);
        }
        
    }

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
