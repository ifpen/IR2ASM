package fr.ifpen.allotropeconverters.ir.spc;

import fr.ifpen.allotropeconverters.ir.schema.*;
import fr.ifpen.allotropeconverters.allotropeutils.AllotropeData;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpcFileToAllotropeMapper {

    private static final String UNITLESS = "(unitless)";
    private final FileParser fileParser = new FileParser();

    public List<FtirEmbedSchema> mapToFtirEmbedSchema(String filePath) throws IOException {

        File inputFile = new File(filePath);

        SpcFileMetadata spcFileMetadata =
                new SpcFileMetadata(
                        filePath,
                        Instant.ofEpochMilli(inputFile.lastModified()));

        SpcFile spcFile = fileParser.parseFile(inputFile);

        HashMap<String, String> logData = parseLog(spcFile.log());


        return spcFile.dataBlocks().stream().map(
                db -> createFtirEmbedSchemaFromSpcSpectrum(db, logData, spcFileMetadata)).toList();
    }

    private HashMap<String, String> parseLog(String log){
        Pattern p = Pattern.compile("^([^\\n\\r=]*)=([^\\n\\r]*)$", Pattern.MULTILINE);
        Matcher m = p.matcher(log);

        HashMap<String, String> result = new HashMap<>();

        while (m.find()){
            result.put(m.group(1), m.group(2));
        }

        return result;
    }

    private FtirEmbedSchema createFtirEmbedSchemaFromSpcSpectrum(
            SpcFileSpectrum spcFileSpectrum,
            HashMap<String, String> log,
            SpcFileMetadata spcFileMetadata){

        AllotropeData data = createAllotropeDataFromSpcSpectrum(spcFileSpectrum);

        TransmittanceSpectrumDataCube transmittanceSpectrumDataCube = new TransmittanceSpectrumDataCube();
        transmittanceSpectrumDataCube.setLabel("Transmittance Data");
        transmittanceSpectrumDataCube.setCubeStructure(generateDataCube());
        transmittanceSpectrumDataCube.setData(data);

        MeasurementDocument measurementDocument =
                createMeasurementDocument(transmittanceSpectrumDataCube, log);

        FourierTransformInfraredDocument fourierTransformInfraredDocument=
                createFourierTransformInfraredDocument(measurementDocument);

        return createFtirFile(fourierTransformInfraredDocument, log, spcFileMetadata);
    }

    private AllotropeData createAllotropeDataFromSpcSpectrum
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

    private MeasurementDocument createMeasurementDocument(
            TransmittanceSpectrumDataCube transmittanceSpectrumDataCube,
            HashMap<String, String> log)
    {
        final String RESOLUTION_KEY = "Inst. Resolution";
        final String AVERAGES_KEY = "Coadd Nb of coadditions";
        final String DETECTOR_GAIN_SETTING_NUMBER_KEY = "(c)  Det. Default FS gain";
        final String DETECTOR_GAIN_SETTING_PREFIX = "(c) Det. First Stage Gain";

        MeasurementDocument measurementDocument = new MeasurementDocument();
        measurementDocument.setOpticalVelocitySetting(generateDefaultOpticalVelocitySetting());

        Resolution resolution = new Resolution();
        resolution.setUnit("1/cm");
        resolution.setValue(Double.parseDouble(log.get(RESOLUTION_KEY)));
        resolution.setType("");
        measurementDocument.setResolution(resolution);

        NumberOfAverages numberOfAverages = new NumberOfAverages();
        numberOfAverages.setValue(Double.parseDouble(log.get(AVERAGES_KEY)));
        numberOfAverages.setUnit(UNITLESS);
        numberOfAverages.setType("");
        measurementDocument.setNumberOfAverages(numberOfAverages);

        measurementDocument.setApertureSizeSetting(generateDefaultApertureSizeSetting());

        String detectorGainSettingNumber = log.get(DETECTOR_GAIN_SETTING_NUMBER_KEY);
        DetectorGainSetting detectorGainSetting = new DetectorGainSetting();
        detectorGainSetting.setValue(
                Double.parseDouble(log.get(DETECTOR_GAIN_SETTING_PREFIX + detectorGainSettingNumber)));
        detectorGainSetting.setUnit(UNITLESS);
        detectorGainSetting.setType("");
        measurementDocument.setDetectorGainSetting(detectorGainSetting);

        measurementDocument.setTransmittanceSpectrumDataCube(transmittanceSpectrumDataCube);
        measurementDocument.setInfraredInterferogramDataCube(new InfraredInterferogramDataCube());
        measurementDocument.setReflectanceSpectrumDataCube(new ReflectanceSpectrumDataCube());
        measurementDocument.setAbsorptionSpectrumDataCube(new AbsorptionSpectrumDataCube());

        return measurementDocument;
    }

    private FourierTransformInfraredDocument createFourierTransformInfraredDocument(
            MeasurementDocument measurementDocument){
        FourierTransformInfraredDocument fourierTransformInfraredDocument =
                new FourierTransformInfraredDocument();
        fourierTransformInfraredDocument.setMeasurementDocument(measurementDocument);

        return fourierTransformInfraredDocument;
    }

    private FtirEmbedSchema createFtirFile(
            FourierTransformInfraredDocument fourierTransformInfraredDocument,
            HashMap<String, String> log,
            SpcFileMetadata spcFileMetadata){

        final String EQUIPMENT_SERIAL_NUMBER_KEY = "(c) Inst. Serial ID Part#1";
        final String MEASUREMENT_IDENTIFIER_KEY = "ObjectID";
        final String SAMPLE_IDENTIFIER_KEY = "Comment";

        FtirEmbedSchema ftir = new FtirEmbedSchema();
        ftir.setBeamsplitterType("ZnSe");
        ftir.setEquipmentSerialNumber(log.get(EQUIPMENT_SERIAL_NUMBER_KEY));
        ftir.setMeasurementIdentifier(log.get(MEASUREMENT_IDENTIFIER_KEY));
        ftir.setMeasurementTime(spcFileMetadata.lastModifiedDate());
        ftir.setSampleIdentifier(log.get(SAMPLE_IDENTIFIER_KEY));
        ftir.setExperimentalDataIdentifier(spcFileMetadata.path());
        ftir.setDetectorType("DTGS");
        ftir.setAnalyst("N/A");
        ftir.setFourierTransformInfraredDocument(fourierTransformInfraredDocument);

        return ftir;
    }

    private OpticalVelocitySetting generateDefaultOpticalVelocitySetting(){
        OpticalVelocitySetting defaultOpticalVelocitySetting = new OpticalVelocitySetting();
        defaultOpticalVelocitySetting.setUnit("cm/s");
        defaultOpticalVelocitySetting.setType("");
        defaultOpticalVelocitySetting.setValue((double) 0);

        return defaultOpticalVelocitySetting;
    }

    private ApertureSizeSetting generateDefaultApertureSizeSetting(){
        ApertureSizeSetting defaultApertureSizeSetting = new ApertureSizeSetting();
        defaultApertureSizeSetting.setUnit("nm");
        defaultApertureSizeSetting.setType("");
        defaultApertureSizeSetting.setValue((double) 0);

        return  defaultApertureSizeSetting;
    }

    private CubeStructure generateDataCube(){
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
