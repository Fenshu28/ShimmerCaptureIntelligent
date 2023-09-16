/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 16 sep. 2023
 * Fecha de modificación: 16 sep. 2023
 * Descripción: Clase para representar un dispositivo shimmer.
 *********************************************** */
package entity;

import com.shimmerresearch.pcDriver.ShimmerPC;

public class ShimmerDispositive extends ShimmerPC {
    private final ShimmerPC device;

    public ShimmerDispositive(ShimmerPC device) {
        this.device = device;
    }

    public ShimmerPC getDevice() {
        return device;
    }
       
    public int getBatteryLevel(){
        return (int) device.getBattStatusDetails().
                getEstimatedChargePercentage();
    }
}
