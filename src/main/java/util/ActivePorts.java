package util;

import java.util.ArrayList;
import java.util.List;
import com.fazecast.jSerialComm.*;
import javax.swing.JOptionPane;

public class ActivePorts {

    private List<String> ports;

    public ActivePorts() {
        ports = new ArrayList<>();
        SerialPort.getCommPorts();
    }

    private void loadPorts() {
        try {
            SerialPort[] ports = SerialPort.getCommPorts();
            for (SerialPort port : ports) {
                //ports.add(port.getSystemPortName());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudieron cargarlos puertos por: " + e.getMessage(),
                    "¡Error inesperado!",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public List<String> getPorts() {
        loadPorts();
        return ports;
    }
}
