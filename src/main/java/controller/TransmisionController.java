/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 18 sep. 2023
 * Fecha de modificación: 18 sep. 2023
 * Descripción: Clase para controllar la transmisión.
 *********************************************** */
package controller;

import com.shimmerresearch.biophysicalprocessing.ECGtoHRAdaptive;
import com.shimmerresearch.driver.Configuration.Shimmer3.DerivedSensorsBitMask;
import com.shimmerresearch.driverUtilities.SensorDetails;
import com.shimmerresearch.exceptions.ShimmerException;
import com.shimmerresearch.algorithms.Filter;
import com.shimmerresearch.biophysicalprocessing.PPGtoHRAlgorithm;
import com.shimmerresearch.driver.Configuration;
import com.shimmerresearch.driver.FormatCluster;
import com.shimmerresearch.driver.ObjectCluster;
import com.shimmerresearch.driver.ShimmerMsg;
import com.shimmerresearch.driverUtilities.AssembleShimmerConfig;
import com.shimmerresearch.driverUtilities.ChannelDetails;
import com.shimmerresearch.pcDriver.ShimmerPC;
import com.shimmerresearch.sensors.SensorGSR;
import com.shimmerresearch.sensors.SensorPPG;
import com.shimmerresearch.sensors.bmpX80.SensorBMP180;
import com.shimmerresearch.tools.bluetooth.BasicShimmerBluetoothManagerPc;
import entity.ShimmerDispositive;
import entity.FileCSV;
import java.util.Collection;

public class TransmisionController {

    // Propiedades del cliente.
    private final FileCSV file;
    private String markExp;
    private String markDinamic;
    private final ShimmerDispositive shimmerDevice;
    private Double lastHR;
    // Propiedades del shimmer.
    private ShimmerMsg shimmerMSG;
    private Filter lpf = null, hpf = null;
    private PPGtoHRAlgorithm heartRateCalculation;
    // Controlador.
    private final FileCsvController file_Controlle;

    public TransmisionController(FileCSV file, ShimmerDispositive shimmerDevice,
            PPGtoHRAlgorithm heartRateCalculation) {
        this.file = file;
        this.shimmerDevice = shimmerDevice;
        this.heartRateCalculation = heartRateCalculation;

        file_Controlle = new FileCsvController(this.file);
    }

    public void setMarkExp(String markExp) {
        this.markExp = markExp;
    }

    public void setMarkDinamic(String markDinamic) {
        this.markDinamic = markDinamic;
    }

    public void setShimmerMSG(ShimmerMsg shimmerMSG) {
        this.shimmerMSG = shimmerMSG;
    }

    public void setLpf(Filter lpf) {
        this.lpf = lpf;
    }

    public void setHpf(Filter hpf) {
        this.hpf = hpf;
    }

    public void log() {
        getDataGSR(); // 0,3 - Se agrega el GSR (Conductancia y resistancia).
        getDataHR(); // 4,5 - Se agrega el HR CAL y el proc.
        getDataTemperatura(); // 5,6 - Se agrega la temperatura.
        getDataPPG(); // 7,8 - Se agrega el PPG.
        addMarks();

        // Guarda los datos en el archivo
        file_Controlle.saveData(shimmerDevice.getData());
    }

    public void configPPG(BasicShimmerBluetoothManagerPc bluetoothManager) {
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
    }

    private void getDataHR() {
        double dataArrayPPG = 0;
        double heartRate = Double.NaN;
        int INVALID_RESULT = -1;

        ObjectCluster objc = (ObjectCluster) shimmerMSG.mB; // Obtiene el Objeto que guarda los datos

        Collection<FormatCluster> adcFormats = objc.getCollectionOfFormatClusters(
                SensorPPG.ObjectClusterSensorName.PPG_A13); // Obtiene el dato del PPG
        FormatCluster format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data

        if (format != null) {
            dataArrayPPG = format.mData;

            try {
                dataArrayPPG = lpf.filterData(dataArrayPPG);
                dataArrayPPG = hpf.filterData(dataArrayPPG);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Obtiene el tiempo.
            Collection<FormatCluster> formatTS = objc.getCollectionOfFormatClusters(
                    Configuration.Shimmer3.ObjectClusterSensorName.TIMESTAMP);
            FormatCluster ts = ObjectCluster.returnFormatCluster(
                    formatTS, "CAL");
            double ppgTimeStamp = ts.mData;

            heartRate = heartRateCalculation.ppgToHrConversion(dataArrayPPG, ppgTimeStamp);

            if (heartRate == INVALID_RESULT) {
                heartRate = Double.NaN;
            }

            // Aquí guardar los datos.
            shimmerDevice.getData().add(String.valueOf(heartRate));

            if (heartRate == Double.NaN) {
                shimmerDevice.getData().add(String.valueOf(lastHR));
            } else {
                shimmerDevice.getData().add("");
            }

            lastHR = heartRate;
        } else {
            System.out.println("ERROR! FormatCluster is Null!");
        }
    }

    private void getDataPPG() {
        double ppgCal = Double.NaN;
        double ppgRaw = Double.NaN;

        ObjectCluster objc = (ObjectCluster) shimmerMSG.mB; // Obtiene el Objeto que guarda los datos

        Collection<FormatCluster> adcFormats = objc.getCollectionOfFormatClusters(
                SensorPPG.ObjectClusterSensorName.PPG_A13); // Obtiene el dato del PPG
        FormatCluster format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data

        ppgCal = format.mData;

        format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.UNCAL.toString()));

        ppgRaw = format.mData;

        shimmerDevice.getData().add(String.valueOf(ppgCal));
        shimmerDevice.getData().add(String.valueOf(ppgRaw));
    }

    private void getDataGSR() {
        double gsrCal = Double.NaN;
        double gsrRaw = Double.NaN;

        ObjectCluster objc = (ObjectCluster) shimmerMSG.mB; // Obtiene el Objeto que guarda los datos

        // Obteniendo la conductancia.
        Collection<FormatCluster> adcFormats = objc.getCollectionOfFormatClusters(
                SensorGSR.ObjectClusterSensorName.GSR_CONDUCTANCE); // Obtiene el dato del GSR conductancia

        FormatCluster format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data

        gsrCal = format.mData;

        format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.UNCAL.toString()));

        gsrRaw = format.mData;

        shimmerDevice.getData().add(String.valueOf(gsrCal));
        shimmerDevice.getData().add(String.valueOf(gsrRaw));

        // Obteniendo la resistancia.
        adcFormats = objc.getCollectionOfFormatClusters(
                SensorGSR.ObjectClusterSensorName.GSR_RESISTANCE);
        format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data

        gsrCal = format.mData;

        format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.UNCAL.toString()));

        gsrRaw = format.mData;

        shimmerDevice.getData().add(String.valueOf(gsrCal));
        shimmerDevice.getData().add(String.valueOf(gsrRaw));
    }

    private void getDataTemperatura() {
        double tempCal = Double.NaN;
        double tempRaw = Double.NaN;

        ObjectCluster objc = (ObjectCluster) shimmerMSG.mB; // Obtiene el Objeto que guarda los datos

        Collection<FormatCluster> adcFormats = objc.getCollectionOfFormatClusters(
                SensorBMP180.ObjectClusterSensorName.TEMPERATURE_BMP180); // Obtiene el dato del PPG
        FormatCluster format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data

        tempCal = format.mData;

        format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.UNCAL.toString()));

        tempRaw = format.mData;

        shimmerDevice.getData().add(String.valueOf(tempCal));
        shimmerDevice.getData().add(String.valueOf(tempRaw));
    }

    private void addMarks() {
        shimmerDevice.getData().add(markExp);
        shimmerDevice.getData().add(markDinamic);
    }
}
