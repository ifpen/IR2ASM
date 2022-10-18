package fr.ifpen.spectrum.ir.parser;

import com.google.common.io.LittleEndianDataInputStream;
import fr.ifpen.spectrum.ir.SpcFileHeader;
import fr.ifpen.spectrum.ir.flags.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")

public class SpcHeaderParser {

    private SpcHeaderParser(){}

    public static SpcFileHeader parseHeader(
            LittleEndianDataInputStream inputStream, Instant fileLastModifiedDate) throws IOException {
        int featureFlag = inputStream.readUnsignedByte();
        int versionFlag = inputStream.readUnsignedByte();
        int experimentTypeFlag = inputStream.readUnsignedByte();
        int exponent = inputStream.readByte();
        int numberOfPoints = inputStream.readInt();
        double startingX = inputStream.readDouble();
        double endingX = inputStream.readDouble();
        int subfileCount = inputStream.readInt();
        int xUnitFlag = inputStream.readUnsignedByte();
        int yUnitFlag = inputStream.readUnsignedByte();
        int zUnitFlag = inputStream.readUnsignedByte();
        inputStream.readUnsignedByte(); //Fpost - "Do not use when creating data file conversion routines should normally be set to null." - Ignoring this parameter here.
        int intDate = inputStream.readInt(); //Fdate - In all the test data set to 0.
        String resolutionDescription = Utility.readCharacters(inputStream, 9); //Length fixed in format documentation.
        String sourceInstrument = Utility.readCharacters(inputStream, 9); //Length fixed in format documentation.
        int peakNumberPoint = inputStream.readUnsignedShort();

        for (int i = 0; i < 8; i++) { //Skipping 8 floats of spare, not used in the test data.
            inputStream.readFloat();
        }

        String memo = Utility.readCharacters(inputStream, 130); //Length fixed in format documentation.
        String xyzLabels = Utility.readCharacters(inputStream, 30);

        int offsetToLog = inputStream.readInt();
        inputStream.readInt(); // Fmods - "Have been supplanted by the use of textual parameters in the LogText block"
        inputStream.readUnsignedByte(); // FProcs - "Do not use when creating data file conversion routines should normally be set to null." - Ignoring this parameter here.
        inputStream.readUnsignedByte(); // Flevel - "For Galactic internal use only; do not use. Must be set to null."
        inputStream.readUnsignedShort(); //Fsampin - "For Galactic internal use only; do not use. Must be set to null."
        inputStream.readFloat(); //Ffactor - "For Galactic internal use only; do not use. Must be set to null."
        Utility.readCharacters(inputStream, 48); //Fmethod - "For Galactic internal use only; do not use. Must be set to null."
        inputStream.readFloat(); //ZIncrement - Not used in the model.
        inputStream.readInt(); //wPlanes - Not used in the model.
        inputStream.readFloat(); //wIncrement - Not used in the model.
        inputStream.readUnsignedByte(); //wAxisFlag - Not used in the model.
        Utility.readCharacters(inputStream, 187); //Freserv - "Do not write any data in this area."

        Set<SpcFileFeatureEnum> features = Utility.getDataFlags(featureFlag);

        return new SpcFileHeader(
                Utility.getEnumFromFlag(versionFlag, SpcFileVersionEnum.class),
                features,
                Utility.getEnumFromFlag(experimentTypeFlag, SpcExperimentTypeEnum.class),
                exponent,
                numberOfPoints,
                startingX,
                endingX,
                subfileCount,
                Utility.getEnumFromFlag(xUnitFlag, SpcFileXUnitLabelEnum.class),
                Utility.getEnumFromFlag(yUnitFlag, SpcFileYUnitLabelEnum.class),
                Utility.getEnumFromFlag(zUnitFlag, SpcFileXUnitLabelEnum.class),
                parseDate(intDate, fileLastModifiedDate),
                resolutionDescription,
                sourceInstrument,
                peakNumberPoint,
                memo,
                offsetToLog,
                xyzLabels
                );
    }

private static Instant parseDate(int intDate, Instant fileDate){
    try{
        if(intDate == 0){
            return fileDate;
        } else {
            throw new IllegalArgumentException("Date parsing not implementing, please file an incident");
        }
    } catch (IllegalArgumentException e){
        return Instant.ofEpochSecond(0);
    }
}

}
