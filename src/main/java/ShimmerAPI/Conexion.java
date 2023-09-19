/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 15-09-2023
 * Fecha de modificación: 15-09-2023
 * Descripción: Clase para crear administrar la conexión con el dispositivo
 * Shimmer.
 *********************************************** */
package ShimmerAPI;

import com.shimmerresearch.algorithms.Filter;
import com.shimmerresearch.biophysicalprocessing.PPGtoHRAlgorithm;
import com.shimmerresearch.driver.BasicProcessWithCallBack;
import com.shimmerresearch.driver.CallbackObject;
import com.shimmerresearch.driver.Configuration;
import com.shimmerresearch.driver.FormatCluster;
import com.shimmerresearch.driver.ObjectCluster;
import com.shimmerresearch.driver.ShimmerMsg;
import com.shimmerresearch.driverUtilities.AssembleShimmerConfig;
import com.shimmerresearch.driverUtilities.ChannelDetails;
import com.shimmerresearch.pcDriver.ShimmerPC;
import com.shimmerresearch.sensors.SensorPPG;
import com.shimmerresearch.tools.bluetooth.BasicShimmerBluetoothManagerPc;
import entity.ShimmerDispositive;
import java.util.Collection;
import javax.swing.JOptionPane;
import resource.StatusConection;

public class Conexion extends BasicProcessWithCallBack {
//    ShimmerDispositive shimmer_Dispo;

    private ShimmerDispositive shimmerDevice = new ShimmerDispositive(new ShimmerPC("ShimmerDevice"));//Shimmer3-9415
    static BasicShimmerBluetoothManagerPc bluetoothManager
            = new BasicShimmerBluetoothManagerPc();
    private PPGtoHRAlgorithm heartRateCalculation;
    private boolean mConfigureOnFirstTime = true;
    //Put your device COM port here:
    private String deviceComPort = new String();
    private String status;
    private String status_Stream;
    Filter lpf = null, hpf = null;

    public Conexion() {
        this.status = "Desconectado";
        this.status_Stream = "Stop";
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

    public void conectar() {
        bluetoothManager.connectShimmerThroughCommPort(deviceComPort);
        setWaitForData(bluetoothManager.callBackObject);
    }

    public void desconectar() {
        try {
            bluetoothManager.disconnectShimmer(shimmerDevice.getDevice());
//            shimmerDevice.getDevice().disconnect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Error al desconectar dispositivo",
                    JOptionPane.ERROR_MESSAGE);
        }

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
                                ShimmerPC cloneDevice
                                        = shimmerDevice.getDevice().deepClone();
                                cloneDevice.setSensorEnabledState(
                                        Configuration.Shimmer3.SENSOR_ID.HOST_PPG_A13, true);
                                AssembleShimmerConfig.
                                        generateSingleShimmerConfig(
                                                cloneDevice,
                                                Configuration.COMMUNICATION_TYPE.BLUETOOTH);
                                bluetoothManager.configureShimmer(
                                        cloneDevice);
                                shimmerDevice.getDevice().
                                        writeShimmerAndSensorsSamplingRate(128);
                                heartRateCalculation = new PPGtoHRAlgorithm(shimmerDevice.getDevice().getSamplingRateShimmer(), 5, 10);

                                try {
                                    double[] cutoff = {5.0};
                                    lpf = new Filter(Filter.LOW_PASS, shimmerDevice.getDevice().getSamplingRateShimmer(), cutoff);
                                    cutoff[0] = 0.5;
                                    hpf = new Filter(Filter.HIGH_PASS, shimmerDevice.getDevice().getSamplingRateShimmer(), cutoff);
                                } catch (Exception e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }

                                mConfigureOnFirstTime = false;
                            }
                            //shimmerDevice.getDevice().startStreaming();
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
                if (msg == ShimmerPC.NOTIFICATION_SHIMMER_FULLY_INITIALIZED) { // Dispositivo inicializado.
                    status_Stream = StatusConection.Inicializado.toString();
                    System.out.println("status stream: " + status_Stream);
                }
                switch (msg) {
                    case ShimmerPC.NOTIFICATION_SHIMMER_STOP_STREAMING: // Iniciando transmisión.
                        status_Stream = StatusConection.Parado.toString();
                        System.out.println("status stream: " + status_Stream);
                        break;
                    case ShimmerPC.NOTIFICATION_SHIMMER_START_STREAMING: // Parando transmisión
                        status_Stream = StatusConection.Transmitiendo.toString();
                        System.out.println("status stream: " + status_Stream);
                        break;
                    default:
                        break;
                }
                break;
            }
            case ShimmerPC.MSG_IDENTIFIER_DATA_PACKET: // Recibiendo paquetes de datos.
                double dataArrayPPG = 0;
                double heartRate = Double.NaN;
                int INVALID_RESULT = -1;
                ObjectCluster objc = (ObjectCluster) shimmerMSG.mB;

                Collection<FormatCluster> adcFormats = objc.getCollectionOfFormatClusters(SensorPPG.ObjectClusterSensorName.PPG_A13);
                FormatCluster format = ((FormatCluster) ObjectCluster.returnFormatCluster(adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data

                if (format != null) {
                    dataArrayPPG = format.mData;

                    try {
                        dataArrayPPG = lpf.filterData(dataArrayPPG);
                        dataArrayPPG = hpf.filterData(dataArrayPPG);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    Collection<FormatCluster> formatTS = objc.getCollectionOfFormatClusters(Configuration.Shimmer3.ObjectClusterSensorName.TIMESTAMP);
                    FormatCluster ts = ObjectCluster.returnFormatCluster(formatTS, "CAL");
                    double ppgTimeStamp = ts.mData;
                    heartRate = heartRateCalculation.ppgToHrConversion(dataArrayPPG, ppgTimeStamp);
                    if (heartRate == INVALID_RESULT) {
                        heartRate = Double.NaN;
                    }

                    System.out.println("Heart rate: " + heartRate); // Aquí guardarlos datos
                } else {
                    System.out.println("ERROR! FormatCluster is Null!");
                }

                break;
            case ShimmerPC.MSG_IDENTIFIER_PACKET_RECEPTION_RATE_OVERALL:
                break;
            default:
                break;
        }

    }
}
