/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 20 sep. 2023
 * Descripción: Clase para controlar los datos que se crearan en el CSV.
 *********************************************** */
package controller;

import entity.FileCSV;
import java.util.ArrayList;
import java.util.List;

public class FileCsvController {

    private FileCSV file;

    public FileCsvController(FileCSV file) {
        this.file = file;
    }

    public void saveData(List<String> data) {
        file.setData(data);
    }

    public void setHeaders() {
        List<String> cabeceras = new ArrayList<>();
        cabeceras.add("TimeStamp");
        cabeceras.add("GSR Conductancia CAL");
        cabeceras.add("GSR Conductancia RAW");
        cabeceras.add("GSR Resistancia CAL");
        cabeceras.add("GSR Resistancia RAW");
        cabeceras.add("HR CAL");
//        cabeceras.add("Temperatura CAL");
//        cabeceras.add("Temperatura RAW");
        cabeceras.add("PPG CAL");
        cabeceras.add("PPG RAW");
        cabeceras.add("Marcador experimentos");
        cabeceras.add("Marcador dinamico");

        file.setColumns(cabeceras);
    }
}
