/** **********************************************
 * Autor: Cristopher Alexis Zarate Valencia
 * Fecha de creación: 18 sep. 2023
 * Fecha de modificación: 18 sep. 2023
 * Descripción: Clase para controllar la transmisión.
 *********************************************** */
package controller;

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
    private FileCSV file;
    private String markExp;
    private String markDinamic;
    private final ShimmerDispositive shimmerDevice;

    // Temps
    private double lastHR = 0;
    private double lastGsrCalCond = 0;
    private double lastGsrRawCond = 0;
    private double lastGsrCalRes = 0;
    private double lastGsrRawRes = 0;
    private double lastPpgCal = 0;
    private double lastPpgRaw = 0;
    private double lastTempCal = 0;
    private double lastTempRaw = 0;

    private double[] datosTemp;

    // Propiedades del shimmer.
    private ShimmerMsg shimmerMSG;
    private Filter lpf, hpf;
    private PPGtoHRAlgorithm heartRateCalculation;
    // Controlador.
    private FileCsvController file_Controlle;

    public TransmisionController(ShimmerDispositive shimmerDevice) {
        this.shimmerDevice = shimmerDevice;
        this.datosTemp = new double[8];
    }

    public void setFile(FileCSV file) {
        this.file = file;
        file_Controlle = new FileCsvController(this.file);
        file_Controlle.setHeaders();
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

    public double[] getDatosTemp() {
        return datosTemp;
    }

    public void streamData() {
//        shimmerDevice.getData().clear();
        getTimeStamp(0); // 0 - timestamp
        getDataGSRCond(1); // 1,2 - Se agrega el GSR Conductancia.
        getDataGsrResi(3); // 3,4 - Se agrega el GSR resistancia.
        getDataHR(5); // 5 - Se agrega el HR.
//        getDataTemperatura(); // 5,6 - Se agrega la temperatura.
        getDataPPG(6); // 6,7 - Se agrega el PPG.
        addMarks(8);
//        saveDataTemp();
    }

    public void log() {
        // Guarda los datos en el archivo
        file_Controlle.saveData(shimmerDevice.getData());
    }

    private void saveDataTemp() {
        int i = 1;
        for (double d : datosTemp) {
            d = Double.parseDouble(shimmerDevice.getData().get(i));
            i++;
        }
    }

    /**
     * Clase que configura el PPG con los parametros del tiempo para calcular de
     * forma correcta el HR.
     *
     * @param bluetoothManager Administrador bluetooth que contiene la conexion
     * del dispositivo.
     */
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

    /**
     * Obtiene el Timestamp del dispositivo.
     */
    private void getTimeStamp(int pos) {
        // Obtiene el Objeto que guarda los datos
        ObjectCluster objc = (ObjectCluster) shimmerMSG.mB;
        // Obtiene el tiempo.
        Collection<FormatCluster> formatTS = objc.getCollectionOfFormatClusters(
                Configuration.Shimmer3.ObjectClusterSensorName.TIMESTAMP);
        FormatCluster ts = ObjectCluster.returnFormatCluster(
                formatTS, "CAL");
        double timeStamp = ts.mData;

        shimmerDevice.getData().set(pos, String.valueOf(timeStamp));
    }

    /**
     * Obtiene el HR calculandolo apartir del tiempo y el PPG.
     */
    private void getDataHR(int pos) {
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

            // Aquí guardar los datos.
            if (heartRate == INVALID_RESULT) {
//                heartRate = Double.NaN;
                shimmerDevice.getData().set(pos, String.valueOf(lastHR));
            } else {
                shimmerDevice.getData().set(pos, String.valueOf(heartRate));
                lastHR = heartRate;
            }

//            if (heartRate == Double.NaN) {
//
//            } else {
//
//            }
        } else {
            System.out.println("ERROR! FormatCluster is Null!");
        }
    }

    /**
     * Obtiene el PPG crudo y calculado en mVolts.
     */
    private void getDataPPG(int pos) {
        double ppgCal = Double.NaN;
        double ppgRaw = Double.NaN;

        ObjectCluster objc = (ObjectCluster) shimmerMSG.mB; // Obtiene el Objeto que guarda los datos

        Collection<FormatCluster> adcFormats = objc.getCollectionOfFormatClusters(
                SensorPPG.ObjectClusterSensorName.PPG_A13); // Obtiene el dato del PPG
        FormatCluster format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data

        if (format != null) {
            ppgCal = format.mData;
            lastPpgCal = ppgCal;
        } else {
            ppgCal = lastPpgCal;
        }

        format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.UNCAL.toString()));

        if (format != null) {
            ppgRaw = format.mData;
            lastPpgRaw = ppgRaw;
        } else {
            ppgRaw = lastPpgRaw;
        }

        shimmerDevice.getData().set(pos, String.valueOf(ppgCal));
        shimmerDevice.getData().set(pos + 1, String.valueOf(ppgRaw));

    }

    /**
     * Obtiene la conductancia cruda y calcula en mSimens.
     */
    private void getDataGSRCond(int pos) {
        double gsrCal = Double.NaN;
        double gsrRaw = Double.NaN;

        ObjectCluster objc = (ObjectCluster) shimmerMSG.mB; // Obtiene el Objeto que guarda los datos

        // Obteniendo la conductancia.
        Collection<FormatCluster> adcFormats = objc.getCollectionOfFormatClusters(
                SensorGSR.ObjectClusterSensorName.GSR_CONDUCTANCE); // Obtiene el dato del GSR conductancia
        // Calculada
        FormatCluster format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data
        if (format != null) {
            gsrCal = format.mData;
            lastGsrCalCond = gsrCal;
        } else {
            gsrCal = lastGsrCalCond;
        }
        // Cruda
        format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.UNCAL.toString()));
        if (format != null) {
            gsrRaw = format.mData;
            lastGsrRawCond = gsrRaw;
        } else {
            gsrRaw = lastGsrRawCond;
        }
        shimmerDevice.getData().set(pos, String.valueOf(gsrCal));
        shimmerDevice.getData().set(pos + 1, String.valueOf(gsrRaw));
    }

    /**
     * Obtiene la resistencia cruda y calculada en kOhms.
     */
    private void getDataGsrResi(int pos) {
        double gsrCal = Double.NaN;
        double gsrRaw = Double.NaN;

        ObjectCluster objc = (ObjectCluster) shimmerMSG.mB; // Obtiene el Objeto que guarda los datos

        // Obteniendo la Resistancia.
        Collection<FormatCluster> adcFormats = objc.getCollectionOfFormatClusters(
                SensorGSR.ObjectClusterSensorName.GSR_RESISTANCE); // Obtiene el dato del GSR conductancia
        // Calculada
        FormatCluster format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data
        if (format != null) {
            gsrCal = format.mData;
            lastGsrCalRes = gsrCal;
        } else {
            gsrCal = lastGsrCalRes;
        }

        // Cruda
        format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.UNCAL.toString()));
        if (format != null) {
            gsrRaw = format.mData;
            lastGsrRawRes = gsrRaw;
        } else {
            gsrRaw = lastGsrRawRes;
        }
        shimmerDevice.getData().set(pos, String.valueOf(gsrCal));
        shimmerDevice.getData().set(pos + 1, String.valueOf(gsrRaw));
    }

    /**
     * Obiene la temperatura cruda y calculada en grados Celcius.
     */
    private void getDataTemperatura(int pos) {
        double tempCal = Double.NaN;
        double tempRaw = Double.NaN;

        ObjectCluster objc = (ObjectCluster) shimmerMSG.mB; // Obtiene el Objeto que guarda los datos

        Collection<FormatCluster> adcFormats = objc.getCollectionOfFormatClusters(
                SensorBMP180.ObjectClusterSensorName.TEMPERATURE_BMP180); // Obtiene el dato del PPG
        FormatCluster format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.CAL.toString())); // retrieve the calibrated data
        if (format != null) {
            tempCal = format.mData;
            lastTempCal = tempCal;
        } else {
            tempCal = lastTempCal;
        }
        format = ((FormatCluster) ObjectCluster.returnFormatCluster(
                adcFormats, ChannelDetails.CHANNEL_TYPE.UNCAL.toString()));
        if (format != null) {
            tempRaw = format.mData;
            lastTempRaw = tempRaw;
        } else {
            tempRaw = lastTempRaw;
        }
        shimmerDevice.getData().set(pos, String.valueOf(tempCal));
        shimmerDevice.getData().set(pos + 1, String.valueOf(tempRaw));
    }

    /**
     * Añade las marcas a la lista de datos.
     */
    private void addMarks(int pos) {
        shimmerDevice.getData().set(pos, markExp);
        shimmerDevice.getData().set(pos + 1, markDinamic);
    }
}
