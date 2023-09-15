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
import com.shimmerresearch.driver.ShimmerMsg;
import com.shimmerresearch.driverUtilities.AssembleShimmerConfig;
import com.shimmerresearch.pcDriver.ShimmerPC;
import com.shimmerresearch.tools.bluetooth.BasicShimmerBluetoothManagerPc;

public class Conexion extends BasicProcessWithCallBack {

    ShimmerPC shimmerDevice = new ShimmerPC("ShimmerDevice"); //Shimmer3-9415
    static BasicShimmerBluetoothManagerPc bluetoothManager = new BasicShimmerBluetoothManagerPc();
    private PPGtoHRAlgorithm heartRateCalculation;
    private boolean mConfigureOnFirstTime = true;
    //Put your device COM port here:
    private String deviceComPort;
    private String status;
    Filter lpf = null, hpf = null;

    public String getStatus() {
        return status;
    }

    public void setDeviceComPort(String deviceComPort) {
        this.deviceComPort = deviceComPort;
    }

    public void Conectar() {
        bluetoothManager.connectShimmerThroughCommPort(deviceComPort);
        setWaitForData(bluetoothManager.callBackObject);
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
                            shimmerDevice = (ShimmerPC) bluetoothManager.getShimmerDeviceBtConnected(deviceComPort);
                            //checkECGEnabled();	//Check if ECG is enabled first before streaming
                            //5 beats to average
                            if (mConfigureOnFirstTime) {
                                ShimmerPC cloneDevice = shimmerDevice.deepClone();
                                cloneDevice.setSensorEnabledState(Configuration.Shimmer3.SENSOR_ID.HOST_PPG_A13, true);
                                AssembleShimmerConfig.generateSingleShimmerConfig(cloneDevice, Configuration.COMMUNICATION_TYPE.BLUETOOTH);
                                bluetoothManager.configureShimmer(cloneDevice);
                                shimmerDevice.writeShimmerAndSensorsSamplingRate(128);
                                heartRateCalculation = new PPGtoHRAlgorithm(shimmerDevice.getSamplingRateShimmer(), 5, 10);

                                try {
                                    double[] cutoff = {5.0};
                                    lpf = new Filter(Filter.LOW_PASS, shimmerDevice.getSamplingRateShimmer(), cutoff);
                                    cutoff[0] = 0.5;
                                    hpf = new Filter(Filter.HIGH_PASS, shimmerDevice.getSamplingRateShimmer(), cutoff);
                                } catch (Exception e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }

                                mConfigureOnFirstTime = false;
                            }
                            //shimmerDevice.startStreaming();
                            break;
                        case DISCONNECTED:
                            status = "Desconectado";
                        case CONNECTION_LOST:
                            status = "Conexión perdida";
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
                if (msg == ShimmerPC.NOTIFICATION_SHIMMER_FULLY_INITIALIZED) {
                    try {
//                        shimmerDevice.startStreaming(); // Inicia la transmisión de datos.
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                switch (msg) {
                    case ShimmerPC.NOTIFICATION_SHIMMER_STOP_STREAMING:
                        break;
                    case ShimmerPC.NOTIFICATION_SHIMMER_START_STREAMING:
                        break;
                    default:
                        break;
                }
                break;
            }
            case ShimmerPC.MSG_IDENTIFIER_DATA_PACKET:
//                double dataArrayPPG = 0;
//                double heartRate = Double.NaN;
//                int INVALID_RESULT = -1;
//                ObjectCluster objc = (ObjectCluster) shimmerMSG.mB;
//                Collection<FormatCluster> adcFormats = objc.getCollectionOfFormatClusters(SensorPPG.ObjectClusterSensorName.PPG_A13);
//                FormatCluster format = ((FormatCluster) ObjectCluster.returnFormatCluster(adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data
//                if (format != null) {
//                    dataArrayPPG = format.mData;
//
//                    try {
//                        dataArrayPPG = lpf.filterData(dataArrayPPG);
//                        dataArrayPPG = hpf.filterData(dataArrayPPG);
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                    Collection<FormatCluster> formatTS = objc.getCollectionOfFormatClusters(Configuration.Shimmer3.ObjectClusterSensorName.TIMESTAMP);
//                    FormatCluster ts = ObjectCluster.returnFormatCluster(formatTS, "CAL");
//                    double ppgTimeStamp = ts.mData;
//                    heartRate = heartRateCalculation.ppgToHrConversion(dataArrayPPG, ppgTimeStamp);
//                    if (heartRate == INVALID_RESULT) {
//                        heartRate = Double.NaN;
//                    }
//
//                    System.out.println("Heart rate: " + heartRate); // Aquí guardarlos datos
//                } else {
//                    System.out.println("ERROR! FormatCluster is Null!");
//                }
                break;
            case ShimmerPC.MSG_IDENTIFIER_PACKET_RECEPTION_RATE_OVERALL:
                break;
            default:
                break;
        }

    }
}
