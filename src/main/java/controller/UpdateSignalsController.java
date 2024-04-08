package controller;

import ShimmerAPI.ShimmerAPI;
import globals.Global;
import java.text.DecimalFormat;
import java.util.List;
import resource.StatusConection;

/**
 *
 * @author Arleth Machuca
 */
public class UpdateSignalsController {

    public static void updateLabels(ShimmerAPI api) {
        if (api.getStatus_Stream().contains(
                StatusConection.Transmitiendo.toString())) {
            updateDataLabel(api.getShimmerDevice().getData());
//                    System.out.println("tick");
        }
    }

    public static void updateDataLabel(List<String> data) {
        try {
            Global.frame.getLbGsrCond().setText(
                    new DecimalFormat("#,##0.00")
                            .format(Double.valueOf(data.get(1)))
                    + " mSimens");
            Global.frame.getLbGsrRes().setText(
                    new DecimalFormat("#,##0.00")
                            .format(Double.valueOf(data.get(3))) 
                            + " KOhms");
            Global.frame.getLbHR().setText(
                    new DecimalFormat("#,##0.00")
                            .format(Double.valueOf(data.get(5))) 
                            + " Beats/min.");
            Global.frame.getLbPpg().setText(
                    new DecimalFormat("#,##0.00")
                            .format(Double.valueOf(data.get(6))) 
                            + " mVolts");// Se debe cambiar por 7.
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("nopi");
        }
    }
}
