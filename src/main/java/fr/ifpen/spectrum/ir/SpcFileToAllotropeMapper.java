package fr.ifpen.spectrum.ir;

import fr.ifpen.spectrum.ir.schema.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SpcFileToAllotropeMapper {

    private static final String UNITLESS = "(unitless)";

    private SpcFileToAllotropeMapper(){}
    public static FtirEmbedSchema mapToFtirEmbedSchema(SpcFileSpectrum spcFileSpectrum){

        AllotropeData data = createAllotropeDataFromSpcSpectrum(spcFileSpectrum);

        TransmittanceSpectrumDataCube transmittanceSpectrumDataCube = new TransmittanceSpectrumDataCube();
        transmittanceSpectrumDataCube.setLabel("Transmittance Data");
        transmittanceSpectrumDataCube.setCubeStructure(generateDataCube());
        transmittanceSpectrumDataCube.setData(data);

        MeasurementDocument measurementDocument =
                createMeasurementDocument(transmittanceSpectrumDataCube);

        FourierTransformInfraredDocument fourierTransformInfraredDocument=
                createFourierTransformInfraredDocument(measurementDocument);

        return createFtirFile(fourierTransformInfraredDocument);
    }

    private static AllotropeData createAllotropeDataFromSpcSpectrum
            (SpcFileSpectrum spcFileSpectrum){

        List<List<Double>> dimensions = new ArrayList<>();
        List<List<Double>> measures = new ArrayList<>();

        List<Double> firstDimension = new ArrayList<>();
        List<Double> firstMeasure = new ArrayList<>();

        for (DataPoint datapoint: spcFileSpectrum.dataPoints()) {
            firstDimension.add(datapoint.x());
            firstMeasure.add(datapoint.y());
        }

        dimensions.add(firstDimension);
        measures.add(firstMeasure);

        return new AllotropeData(dimensions, measures);
    }

    private static MeasurementDocument createMeasurementDocument(
            TransmittanceSpectrumDataCube transmittanceSpectrumDataCube)
    {
        MeasurementDocument measurementDocument = new MeasurementDocument();
        measurementDocument.setOpticalVelocitySetting(generateDefaultOpticalVelocitySetting());
        measurementDocument.setResolution(generateDefaultResolution());
        measurementDocument.setNumberOfAverages(generateDefaultNumberOfAverages());
        measurementDocument.setApertureSizeSetting(generateDefaultApertureSizeSetting());
        measurementDocument.setDetectorGainSetting(generateDefaultDetectorGainSetting());
        measurementDocument.setTransmittanceSpectrumDataCube(transmittanceSpectrumDataCube);
        measurementDocument.setInfraredInterferogramDataCube(new InfraredInterferogramDataCube());
        measurementDocument.setReflectanceSpectrumDataCube(new ReflectanceSpectrumDataCube());
        measurementDocument.setAbsorptionSpectrumDataCube(new AbsorptionSpectrumDataCube());

        return measurementDocument;
    }

    private static FourierTransformInfraredDocument createFourierTransformInfraredDocument(
            MeasurementDocument measurementDocument){
        FourierTransformInfraredDocument fourierTransformInfraredDocument =
                new FourierTransformInfraredDocument();
        fourierTransformInfraredDocument.setMeasurementDocument(measurementDocument);

        return fourierTransformInfraredDocument;
    }

    private static FtirEmbedSchema createFtirFile(
            FourierTransformInfraredDocument fourierTransformInfraredDocument){
        ZonedDateTime defaultTime =
                LocalDateTime.of(1970,1,1,0,0,0)
                        .atZone(ZoneId.of("Europe/Paris"));

        FtirEmbedSchema ftir = new FtirEmbedSchema();
        ftir.setBeamsplitterType("N/A");
        ftir.setEquipmentSerialNumber("N/A");
        ftir.setMeasurementIdentifier("N/A");
        ftir.setMeasurementTime(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(defaultTime));
        ftir.setSampleIdentifier("N/A");
        ftir.setDetectorType("N/A");
        ftir.setAnalyst("N/A");
        ftir.setFourierTransformInfraredDocument(fourierTransformInfraredDocument);

        return ftir;
    }

    private static OpticalVelocitySetting generateDefaultOpticalVelocitySetting(){
        OpticalVelocitySetting defaultOpticalVelocitySetting = new OpticalVelocitySetting();
        defaultOpticalVelocitySetting.setUnit("cm/s");
        defaultOpticalVelocitySetting.setType("");
        defaultOpticalVelocitySetting.setValue((double) 0);

        return defaultOpticalVelocitySetting;
    }

    private static Resolution generateDefaultResolution(){
        Resolution defaultResolution = new Resolution();
        defaultResolution.setUnit("1/cm");
        defaultResolution.setType("");
        defaultResolution.setValue((double) 0);

        return defaultResolution;
    }

    private static NumberOfAverages generateDefaultNumberOfAverages(){
        NumberOfAverages defaultNumberOfAverages = new NumberOfAverages();
        defaultNumberOfAverages.setUnit(UNITLESS);
        defaultNumberOfAverages.setType("");
        defaultNumberOfAverages.setValue((double) 0);

        return defaultNumberOfAverages;
    }

    private static ApertureSizeSetting generateDefaultApertureSizeSetting(){
        ApertureSizeSetting defaultApertureSizeSetting = new ApertureSizeSetting();
        defaultApertureSizeSetting.setUnit("nm");
        defaultApertureSizeSetting.setType("");
        defaultApertureSizeSetting.setValue((double) 0);

        return  defaultApertureSizeSetting;
    }

    private static DetectorGainSetting generateDefaultDetectorGainSetting(){
        DetectorGainSetting defaultDetectorGainSetting = new DetectorGainSetting();
        defaultDetectorGainSetting.setUnit(UNITLESS);
        defaultDetectorGainSetting.setType("");
        defaultDetectorGainSetting.setValue((double) 0);

        return  defaultDetectorGainSetting;
    }

    private static CubeStructure generateDataCube(){
        CubeStructure dataCube = new CubeStructure();
        List<Dimension> dimensions = new ArrayList<>();
        List<Measure> measures = new ArrayList<>();

        Dimension firstDimension = new Dimension();
        firstDimension.setConcept("length");

        Measure firstMeasure = new Measure();
        firstMeasure.setConcept("relative intensity");
        firstMeasure.setUnit(UNITLESS);

        dimensions.add(firstDimension);
        measures.add(firstMeasure);

        dataCube.setDimensions(dimensions);
        dataCube.setMeasures(measures);

        return dataCube;
    }
}
