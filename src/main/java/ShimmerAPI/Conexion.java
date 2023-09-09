/**
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha: 08/09/2023.
 * Descripción:
 * Clase para crear una conexión con un dispositivo shimmer.
 */
package ShimmerAPI;

import com.shimmerresearch.driver.BasicProcessWithCallBack;
import com.shimmerresearch.driver.CallbackObject;
import com.shimmerresearch.driver.ObjectCluster;
import com.shimmerresearch.driver.ShimmerDevice;
import com.shimmerresearch.driver.ShimmerMsg;
import com.shimmerresearch.pcDriver.ShimmerPC;
import com.shimmerresearch.tools.bluetooth.BasicShimmerBluetoothManagerPc;

public class Conexion extends BasicProcessWithCallBack {

    private ShimmerPC shimmer = new ShimmerPC("ShimmerDevice"); // Importante
    private ShimmerDevice shimmerDevice; // Importante
    private BasicShimmerBluetoothManagerPc btManager = new BasicShimmerBluetoothManagerPc(); // Important
    private String btComm;
    private String status;

    public Conexion(String btComm) {
        this.btComm = btComm;
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

    public void create() {
        btManager.connectShimmerThroughCommPort(btComm);
    }

    public void delete() {
        btManager.disconnectShimmer(shimmer); // Desconeta el shimmer.
    }

    @Override
    protected void processMsgFromCallback(ShimmerMsg sm) {
        int ind = sm.mIdentifier;

        Object object = (Object) sm.mB;
        switch (ind) {
            case ShimmerPC.MSG_IDENTIFIER_STATE_CHANGE: {
                CallbackObject callbackObject = (CallbackObject) object;
                if (null != callbackObject.mState) {
                    switch (callbackObject.mState) {
                        case CONNECTING:
                            status = "Conectando...";
                            break;
                        case CONNECTED:
                            status = "Conectado";
                            shimmer = (ShimmerPC) btManager.
                                    getShimmerDeviceBtConnected(
                                            btComm);
                            break;
                        case DISCONNECTED:
                            status = "Desconectado";
                        case CONNECTION_LOST:
                            status = "Desconectado";
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
                /*if (msg == ShimmerPC.NOTIFICATION_SHIMMER_FULLY_INITIALIZED) {
                    textPaneStatus.setText("device fully initialized");
                }
                if (msg == ShimmerPC.NOTIFICATION_SHIMMER_STOP_STREAMING) {
                    textPaneStatus.setText("device stopped streaming");
                } else if (msg == ShimmerPC.NOTIFICATION_SHIMMER_START_STREAMING) {
                    textPaneStatus.setText("device streaming");
                    comboBoxCRC.setEnabled(false);
                } else {
                }*/
                break;
            }
            default:
                break;
        }
    }

}
