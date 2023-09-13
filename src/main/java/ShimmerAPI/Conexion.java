/**
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha: 08/09/2023.
 * Descripción:
 * Clase para crear una conexión con un dispositivo shimmer.
 */
package ShimmerAPI;

import com.shimmerresearch.algorithms.Filter;
import com.shimmerresearch.biophysicalprocessing.PPGtoHRAlgorithm;
import com.shimmerresearch.bluetooth.ShimmerBluetooth;
import com.shimmerresearch.driver.BasicProcessWithCallBack;
import com.shimmerresearch.driver.CallbackObject;
import com.shimmerresearch.driver.Configuration;
import com.shimmerresearch.driver.FormatCluster;
import com.shimmerresearch.driver.ObjectCluster;
import com.shimmerresearch.driver.ShimmerDevice;
import com.shimmerresearch.driver.ShimmerMsg;
import com.shimmerresearch.driverUtilities.AssembleShimmerConfig;
import com.shimmerresearch.driverUtilities.ChannelDetails;
import com.shimmerresearch.pcDriver.ShimmerPC;
import com.shimmerresearch.sensors.SensorPPG;
import com.shimmerresearch.tools.bluetooth.BasicShimmerBluetoothManagerPc;
import java.util.Collection;

public class Conexion extends BasicProcessWithCallBack {

    private ShimmerPC shimmer = new ShimmerPC("ShimmerDevice"); // Importante
    private ShimmerDevice shimmerDevice; // Importante
    private BasicShimmerBluetoothManagerPc btManager = new BasicShimmerBluetoothManagerPc(); // Important
    private String btComm;
    private String status;

    public Conexion() {

    }

    public ShimmerPC getShimmer() {
        return shimmer;
    }

    public ShimmerDevice getShimmerDevice() {
        return shimmerDevice;
    }

    public BasicShimmerBluetoothManagerPc getBtManager() {
        return btManager;
    }

    public String getBtComm() {
        return btComm;
    }

    public String getStatus() {
        return status;
    }
    
//    public static void main(String[] args) {
//        Conexion c = new Conexion();
//        c.create("COM4");
//        
//    }

    /**
     * Crea la conexión del dispositivo Shimmer a partir de un puerto serial.
     * Inicializa la varible de {@link btComm}.
     * @param btComm Puerto serial para la conexión.
     */
    public void create(String btComm) {
        this.btComm = btComm;
        btManager.connectShimmerThroughCommPort(btComm);
    }

    /**
     * Elimina la conexión del dispositivo Shimmer.
     */
    public void delete() {
        btManager.disconnectShimmer(shimmer); // Desconeta el shimmer.
    }

    @Override
    protected void processMsgFromCallback(ShimmerMsg sm) {
        int ind = sm.mIdentifier;

        Object object = (Object) sm.mB;

        if (ind == ShimmerPC.MSG_IDENTIFIER_STATE_CHANGE) {
            CallbackObject callbackObject = (CallbackObject) object;

            if (callbackObject.mState == ShimmerBluetooth.BT_STATE.CONNECTING) {
                System.out.println("Conectando");
            } else if (callbackObject.mState == ShimmerBluetooth.BT_STATE.CONNECTED) {
                System.out.println("Conectado.......");
                shimmerDevice = (ShimmerPC) btManager.getShimmerDeviceBtConnected(btComm);
                
            } else if (callbackObject.mState == ShimmerBluetooth.BT_STATE.DISCONNECTED
                    //					|| callbackObject.mState == BT_STATE.NONE
                    || callbackObject.mState == ShimmerBluetooth.BT_STATE.CONNECTION_LOST) {
                System.out.println("Desconectado");

            }
        } else if (ind == ShimmerPC.MSG_IDENTIFIER_NOTIFICATION_MESSAGE) {
            CallbackObject callbackObject = (CallbackObject) object;
            int msg = callbackObject.mIndicator;
            if (msg == ShimmerPC.NOTIFICATION_SHIMMER_FULLY_INITIALIZED) {
                try {
                    shimmerDevice.startStreaming();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (msg == ShimmerPC.NOTIFICATION_SHIMMER_STOP_STREAMING) {
                System.out.println("Parado....");
            } else if (msg == ShimmerPC.NOTIFICATION_SHIMMER_START_STREAMING) {

            } else {
            }
        } 
//        else if (ind == ShimmerPC.MSG_IDENTIFIER_DATA_PACKET) {
//            
//        } else if (ind == ShimmerPC.MSG_IDENTIFIER_PACKET_RECEPTION_RATE_OVERALL) {
//
//        }

        /*switch (ind) {
            case ShimmerPC.MSG_IDENTIFIER_STATE_CHANGE ->  {
                CallbackObject callbackObject = (CallbackObject) object;
                //if (null != callbackObject.mState) {
                switch (callbackObject.mState) {
                    case CONNECTING:
                        status = "Conectando...";
                        System.out.println(status);
                        break;
                    case CONNECTED:
                        status = "Conectado";
                        System.out.println(status);
                        shimmer = (ShimmerPC) btManager.
                                getShimmerDeviceBtConnected(
                                        btComm);
                        break;
                    case DISCONNECTED:
                        status = "Desconectado";
                        System.out.println(status);
                    case CONNECTION_LOST:
                        status = "Desconectado";
                        System.out.println(status);
                        break;
                    default:
                        break;
                }
                //}
            }
            case ShimmerPC.MSG_IDENTIFIER_NOTIFICATION_MESSAGE ->  {
                CallbackObject callbackObject = (CallbackObject) object;
                int msg = callbackObject.mIndicator;
                /*if (msg == ShimmerPC.NOTIFICATION_SHIMMER_FULLY_INITIALIZED) {
                textPaneStatus.setText("device fully initialized");
                }
                if (msg == ShimmerPC.NOTIFICATION_SHIMMER_STOP_STREAMING) {
                textPaneStatus.setText("device stopped streaming");
                } else if (msg == ShimmerPC.NOTIFICATION_SHIMMER_START_STREAMING) {
                textPaneStatus.setText("device streaming");
                comboBoxCRC.setEnabled(false);
                } else {
                }            }
            default -> {
            }
        }*/
    }

}
