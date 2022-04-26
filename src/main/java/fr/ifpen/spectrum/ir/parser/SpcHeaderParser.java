package fr.ifpen.spectrum.ir.parser;

import com.google.common.io.LittleEndianDataInputStream;
import fr.ifpen.spectrum.ir.SpcFileHeader;
import fr.ifpen.spectrum.ir.flags.*;

import java.util.EnumSet;

public class SpcHeaderParser {
    public static SpcFileHeader parseHeader(LittleEndianDataInputStream inputStream) throws Exception {
        int featureFlag = inputStream.readUnsignedByte();
        int versionFlag = inputStream.readUnsignedByte();
        int experimentTypeFlag = inputStream.readUnsignedByte();
        int exponent = inputStream.readByte();
        int numberOfPoints = inputStream.readInt();
        double startingX = inputStream.readDouble();
        double endingX = inputStream.readDouble();
        int subfileCount = inputStream.readInt();
        int XUnitFlag = inputStream.readUnsignedByte();
        int YUnitFlag = inputStream.readUnsignedByte();
        int ZUnitFlag = inputStream.readUnsignedByte();
        inputStream.readUnsignedByte(); //Fpost - "Do not use when creating data file conversion routines should normally be set to null." - Ignoring this parameter here.
        inputStream.readInt(); //Fdate - In all the test data set to 0. TODO: Implement this feature
        String resolutionDescription = Utility.readCharacters(inputStream, 9); //Length fixed in format documentation.
        String sourceInstrument = Utility.readCharacters(inputStream, 9); //Length fixed in format documentation.
        int peakNumberPoint = inputStream.readUnsignedShort();

        for (int i = 0; i < 8; i++) { //Skipping 8 floats of spare, not used in the test data. TODO: Implement this feature.
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
        double zIncrement = inputStream.readFloat();
        int wPlanes = inputStream.readInt();
        double wIncrement = inputStream.readFloat();
        int wAxisFlag = inputStream.readUnsignedByte();
        Utility.readCharacters(inputStream, 187); //Freserv - "Do not write any data in this area."

        EnumSet<SpcFileFeatureEnum> features = Utility.getDataFlags(featureFlag);

        return new SpcFileHeader(
                Utility.GetEnumFromFlag(versionFlag, SpcFileVersionEnum.class),
                features,
                Utility.GetEnumFromFlag(experimentTypeFlag, SpcExperimentTypeEnum.class),
                exponent,
                numberOfPoints,
                startingX,
                endingX,
                subfileCount,
                Utility.GetEnumFromFlag(XUnitFlag, SpcFileXUnitLabelEnum.class),
                Utility.GetEnumFromFlag(YUnitFlag, SpcFileYUnitLabelEnum.class),
                Utility.GetEnumFromFlag(ZUnitFlag, SpcFileXUnitLabelEnum.class),
                resolutionDescription,
                sourceInstrument,
                peakNumberPoint,
                memo,
                offsetToLog,
                xyzLabels
                );
    }
}
