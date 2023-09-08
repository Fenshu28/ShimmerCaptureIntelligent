package util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.comm.CommPortIdentifier;
import javax.swing.JOptionPane;

public class ActivePorts {
    private Enumeration puertosSystem = CommPortIdentifier.getPortIdentifiers();
    private CommPortIdentifier cpi;
    private List<String> ports;

    public ActivePorts() {
        ports =  new ArrayList<>();
    }    
    
    private void loadPorts(){
        try {
            while (puertosSystem.hasMoreElements()) {            
            cpi = (CommPortIdentifier) puertosSystem.nextElement();
            ports.add(cpi.getName());
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "No se pudieron cargarlos puertos por: " + e.getMessage(), 
                    "¡Error inesperado!", 
                    JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public List<String> getPorts(){
        loadPorts();
        return ports;
    }
}
