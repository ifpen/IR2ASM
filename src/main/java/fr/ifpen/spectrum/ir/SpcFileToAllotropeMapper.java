package fr.ifpen.spectrum.ir;

import fr.ifpen.spectrum.ir.schema.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SpcFileToAllotropeMapper {
    public static FtirEmbedSchema mapToFtirEmbedSchema(
            SpcFileHeader spcFileHeader,
            SpcFileSpectrum spcFileSpectrum){

        AllotropeData data = CreateAllotropeDataFromSpcSpectrum(spcFileSpectrum);

        TransmittanceSpectrumDataCube transmittanceSpectrumDataCube = new TransmittanceSpectrumDataCube();
        transmittanceSpectrumDataCube.setLabel("Transmittance Data");
        transmittanceSpectrumDataCube.setCubeStructure(GenerateDataCube());
        transmittanceSpectrumDataCube.setData(data);

        MeasurementDocument measurementDocument =
                CreateMeasurementDocument(transmittanceSpectrumDataCube);

        FourierTransformInfraredDocument fourierTransformInfraredDocument=
                CreateFourierTransformInfraredDocument(measurementDocument);

        return CreateFtirFile(fourierTransformInfraredDocument);
    }

    private static AllotropeData CreateAllotropeDataFromSpcSpectrum
            (SpcFileSpectrum spcFileSpectrum){

        AllotropeData data = new AllotropeData();

        List<List<Double>> dimensions = new ArrayList<>();
        List<List<Double>> measures = new ArrayList<>();

        List<Double> firstDimension = new ArrayList<>();
        List<Double> firstMeasure = new ArrayList<>();

        for (DataPoint datapoint: spcFileSpectrum.dataPoints) {
            firstDimension.add(datapoint.x);
            firstMeasure.add(datapoint.y);
        }

        dimensions.add(firstDimension);
        measures.add(firstMeasure);

        data.setDimensions(dimensions);
        data.setMeasures(measures);

        return data;
    }

    private static MeasurementDocument CreateMeasurementDocument(
            TransmittanceSpectrumDataCube transmittanceSpectrumDataCube)
    {
        MeasurementDocument measurementDocument = new MeasurementDocument();
        measurementDocument.setOpticalVelocitySetting(GenerateDefaultOpticalVelocitySetting());
        measurementDocument.setResolution(GenerateDefaultResolution());
        measurementDocument.setNumberOfAverages(GenerateDefaultNumberOfAverages());
        measurementDocument.setApertureSizeSetting(GenerateDefaultApertureSizeSetting());
        measurementDocument.setDetectorGainSetting(GenerateDefaultDetectorGainSetting());
        measurementDocument.setTransmittanceSpectrumDataCube(transmittanceSpectrumDataCube);
        measurementDocument.setInfraredInterferogramDataCube(new InfraredInterferogramDataCube());
        measurementDocument.setReflectanceSpectrumDataCube(new ReflectanceSpectrumDataCube());
        measurementDocument.setAbsorptionSpectrumDataCube(new AbsorptionSpectrumDataCube());

        return measurementDocument;
    }

    private static FourierTransformInfraredDocument CreateFourierTransformInfraredDocument(
            MeasurementDocument measurementDocument){
        FourierTransformInfraredDocument fourierTransformInfraredDocument =
                new FourierTransformInfraredDocument();
        fourierTransformInfraredDocument.setMeasurementDocument(measurementDocument);

        return fourierTransformInfraredDocument;
    }

    private static FtirEmbedSchema CreateFtirFile(
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

    private static OpticalVelocitySetting GenerateDefaultOpticalVelocitySetting(){
        OpticalVelocitySetting defaultOpticalVelocitySetting = new OpticalVelocitySetting();
        defaultOpticalVelocitySetting.setUnit("cm/s");
        defaultOpticalVelocitySetting.setType("");
        defaultOpticalVelocitySetting.setValue((double) 0);

        return defaultOpticalVelocitySetting;
    }

    private static Resolution GenerateDefaultResolution(){
        Resolution defaultResolution = new Resolution();
        defaultResolution.setUnit("1/cm");
        defaultResolution.setType("");
        defaultResolution.setValue((double) 0);

        return defaultResolution;
    }

    private static NumberOfAverages GenerateDefaultNumberOfAverages(){
        NumberOfAverages defaultNumberOfAverages = new NumberOfAverages();
        defaultNumberOfAverages.setUnit("(unitless)");
        defaultNumberOfAverages.setType("");
        defaultNumberOfAverages.setValue((double) 0);

        return defaultNumberOfAverages;
    }

    private static ApertureSizeSetting GenerateDefaultApertureSizeSetting(){
        ApertureSizeSetting defaultApertureSizeSetting = new ApertureSizeSetting();
        defaultApertureSizeSetting.setUnit("nm");
        defaultApertureSizeSetting.setType("");
        defaultApertureSizeSetting.setValue((double) 0);

        return  defaultApertureSizeSetting;
    }

    private static DetectorGainSetting GenerateDefaultDetectorGainSetting(){
        DetectorGainSetting defaultDetectorGainSetting = new DetectorGainSetting();
        defaultDetectorGainSetting.setUnit("(unitless)");
        defaultDetectorGainSetting.setType("");
        defaultDetectorGainSetting.setValue((double) 0);

        return  defaultDetectorGainSetting;
    }

    private static CubeStructure GenerateDataCube(){
        CubeStructure dataCube = new CubeStructure();
        List<Dimension> dimensions = new ArrayList<>();
        List<Measure> measures = new ArrayList<>();

        Dimension firstDimension = new Dimension();
        firstDimension.setConcept("length");

        Measure firstMeasure = new Measure();
        firstMeasure.setConcept("relative intensity");
        firstMeasure.setUnit("(unitless)");

        dimensions.add(firstDimension);
        measures.add(firstMeasure);

        dataCube.setDimensions(dimensions);
        dataCube.setMeasures(measures);

        return dataCube;
    }
}
