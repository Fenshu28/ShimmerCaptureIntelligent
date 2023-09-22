/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 16 sep. 2023
 * Fecha de modificación: 16 sep. 2023
 * Descripción: Clase para representar un dispositivo shimmer.
 *********************************************** */
package entity;

import com.shimmerresearch.driverUtilities.ShimmerBattStatusDetails;
import com.shimmerresearch.pcDriver.ShimmerPC;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ShimmerDispositive extends ShimmerPC {

    private final ShimmerPC device;
    private boolean dataReady = false;
    private List<String> data;

    public ShimmerDispositive(ShimmerPC device) {
        this.device = device;
        data = new ArrayList<>();
    }

    public ShimmerPC getDevice() {
        return device;
    }

    public List<String> getData() {
        return data;
    }

    public boolean isDataReady() {
        return dataReady;
    }

    public void setDataReady(boolean dataReady) {
        this.dataReady = dataReady;
    }

    public int getBatteryLevel() {
        //ShimmerBattStatusDetails.battVoltageToBattPercentage(
        //device.getBattStatusDetails().getBattVoltage())
        System.out.println("batt perce: " + device.getBattStatusDetails().
                getEstimatedChargePercentage());
        return (int) device.getBattStatusDetails().
                getEstimatedChargePercentage();
    }

    public int checkBatteryLevel() {
        if (getBatteryLevel() <= 20 && device.isConnected()) {
            return 1;
        } else {
            return 0;
        }
    }
}
