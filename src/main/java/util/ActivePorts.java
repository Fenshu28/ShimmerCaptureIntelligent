package util;

import java.util.ArrayList;
import java.util.List;
import com.fazecast.jSerialComm.*;
import entity.Port;
import javax.swing.JOptionPane;

public class ActivePorts {

    private List<Port> ports;

    public ActivePorts() {
        
    }
    
    /**
     * Método encargado de cargar los puertos seriales que esten activos en el
     * sistema, guardandolos en la lista {@link ports} de tipo {@link Port}.
     */
    private void loadPorts() {
        ports = new ArrayList<>();
        try {            
            SerialPort[] portSys = SerialPort.getCommPorts();
            for (SerialPort port : portSys) {
                ports.add(new Port(port.getSystemPortName(), 
                        port.getPortDescription()));
                //loadDeviceName(port.getSystemPortName());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudieron cargarlos puertos por: " + e.getMessage(),
                    "¡Error inesperado!",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
    
    /**
     * Método para obtener el nombre del dispositivo que esta usando un
     * determinado puerto.
     * @param puertoSerialNombre 
     * Puerto para analizar.
     */
    private void loadDeviceName(String puertoSerialNombre){
        SerialPort puertoSerial = SerialPort.getCommPort(puertoSerialNombre);
        
        if (puertoSerial.openPort()) {
            System.out.println("Puerto serial abierto: " + puertoSerial.getSystemPortName());
            
            // Envía un comando al dispositivo para obtener información
            String comando = "ObtenerInformacion\r\n"; // Reemplaza con el comando adecuado
            puertoSerial.writeBytes(comando.getBytes(), comando.length());
            
            // Lee la respuesta del dispositivo
            byte[] buffer = new byte[1024];
            int bytesRead = puertoSerial.readBytes(buffer, buffer.length);
            String respuesta = new String(buffer, 0, bytesRead);
            
            System.out.println("Respuesta del dispositivo: " + respuesta);
            
            // Cierra el puerto serial cuando hayas terminado
            puertoSerial.closePort();
        } else {
            System.err.println("No se pudo abrir el puerto serial: " + puertoSerial.getSystemPortName());
        }
    }

    public List<Port> getPorts() {
        loadPorts();
        return ports;
    }
}
