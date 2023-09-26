/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 15-09-2023
 * Fecha de modificación: 15-09-2023
 * Descripción: Clase para crear administrar la conexión con el dispositivo
 * Shimmer.
 *********************************************** */
package ShimmerAPI;

import com.shimmerresearch.driver.BasicProcessWithCallBack;
import com.shimmerresearch.driver.CallbackObject;
import com.shimmerresearch.driver.ShimmerMsg;
import com.shimmerresearch.pcDriver.ShimmerPC;
import com.shimmerresearch.tools.bluetooth.BasicShimmerBluetoothManagerPc;
import controller.TransmisionController;
import entity.FileCSV;
import entity.ShimmerDispositive;
import resource.StatusConection;

public class ShimmerAPI extends BasicProcessWithCallBack {

    private ShimmerDispositive shimmerDevice = new ShimmerDispositive(new ShimmerPC("ShimmerDevice"));//Shimmer3-9415
    static BasicShimmerBluetoothManagerPc bluetoothManager
            = new BasicShimmerBluetoothManagerPc();
    private boolean mConfigureOnFirstTime = true;
    private boolean onRec = false;
    // Propiedades del cliente
    private String deviceComPort = new String();
    private String status;
    private String status_Stream;
    private FileCSV file;
    private String markExp;
    private String markDinamic;

    // Controladores
    private TransmisionController transmicion_Cont;

    public ShimmerAPI() {
        this.status = "Desconectado";
        this.status_Stream = "Stop";
    }

    public String getMarkExp() {
        return markExp;
    }

    public void setMarkExp(String markExp) {
        this.markExp = markExp;
    }

    public String getMarkDinamic() {
        return markDinamic;
    }

    public void setMarkDinamic(String markDinamic) {
        this.markDinamic = markDinamic;
    }

    public String getStatus() {
        return status;
    }

    public void setDeviceComPort(String deviceComPort) {
        this.deviceComPort = deviceComPort;
    }

    public String getStatus_Stream() {
        return status_Stream;
    }

    public ShimmerDispositive getShimmerDevice() {
        return shimmerDevice;
    }

    public FileCSV getFile() {
        return file;
    }

    public void setFile(FileCSV file) {
        this.file = file;
    }

    public boolean isOnRec() {
        return onRec;
    }

    public TransmisionController getTransmicion_Cont() {
        return transmicion_Cont;
    }

    public void conectar() {
        transmicion_Cont = new TransmisionController(shimmerDevice);
        bluetoothManager.connectShimmerThroughCommPort(deviceComPort);
        setWaitForData(bluetoothManager.callBackObject);
    }

    public void desconectar() {
        bluetoothManager.disconnectShimmer(shimmerDevice.getDevice());
    }

    /**
     * Comieza la transmición de paquetes en el Shimmer.
     */
    public void transmitir() {
        shimmerDevice.getDevice().startStreaming();
    }

    /**
     * Para la transmisición de paquetes en el Shimmer.
     */
    public void destransmitir() {
        shimmerDevice.getDevice().stopStreaming();
    }

    public void guardar() {
        file.openFile();
        transmicion_Cont.setFile(file);
        onRec = true;
    }

    public void contGuardar() {
        onRec = true;
    }

    public void cerrarGuardar() {
        onRec = false;
    }

    public void terminarGuardado() {
        cerrarGuardar();
        file.closeFile();
    }

    @Override
    protected void processMsgFromCallback(ShimmerMsg shimmerMSG) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        int ind = shimmerMSG.mIdentifier;

        Object object = (Object) shimmerMSG.mB;

        switch (ind) {
            case ShimmerPC.MSG_IDENTIFIER_STATE_CHANGE: {
                CallbackObject callbackObject = (CallbackObject) object;
                if (null != callbackObject.mState) {
                    switch (callbackObject.mState) {
                        case CONNECTING:
                            status = "Conectando";
                            break;
                        case CONNECTED:
                            status = "Conectado";
                            shimmerDevice = new ShimmerDispositive((ShimmerPC) bluetoothManager.
                                    getShimmerDeviceBtConnected(
                                            deviceComPort));

                            //5 beats to average
                            if (mConfigureOnFirstTime) {

                                transmicion_Cont.configPPG(bluetoothManager);

                                mConfigureOnFirstTime = false;
                            }

                            break;
                        case DISCONNECTED:
                            status = "Desconectado";
                        case CONNECTION_LOST:
                            status = "Desconectado";//"Conexión perdida";
                            break;
                        default:
                            break;
                    }
                }
                break;
            }
            case ShimmerPC.MSG_IDENTIFIER_NOTIFICATION_MESSAGE: {
                CallbackObject callbackObject = (CallbackObject) object;
                int msg = callbackObject.mIndicator;

                switch (msg) {
                    case ShimmerPC.NOTIFICATION_SHIMMER_FULLY_INITIALIZED:// Dispositivo inicializado.
                        status_Stream = StatusConection.Inicializado.toString();
                        System.out.println("status stream: " + status_Stream);
                        transmitir();
                        break;
                    case ShimmerPC.NOTIFICATION_SHIMMER_STOP_STREAMING: // Parando transmisión
                        status_Stream = StatusConection.Parado.toString();
                        System.out.println("status stream: " + status_Stream);

                        break;
                    case ShimmerPC.NOTIFICATION_SHIMMER_START_STREAMING: // Iniciando transmisión.
                        status_Stream = StatusConection.Transmitiendo.toString();
                        System.out.println("status stream: " + status_Stream);
                        break;
                    default:
                        break;
                }
                break;
            }
            case ShimmerPC.MSG_IDENTIFIER_DATA_PACKET: // Recibiendo paquetes de datos.
                transmicion_Cont.setShimmerMSG(shimmerMSG);
                transmicion_Cont.streamData();

                if (onRec) {
                    transmicion_Cont.setMarkExp(markExp);
                    transmicion_Cont.setMarkDinamic(markDinamic);
                    transmicion_Cont.log();
                }
                break;
            case ShimmerPC.MSG_IDENTIFIER_PACKET_RECEPTION_RATE_OVERALL:
                break;
            default:
                break;
        }

    }
}
