package fr.ifpen.allotropeconverters.ir.spc;

import com.google.common.io.LittleEndianDataInputStream;
import fr.ifpen.allotropeconverters.ir.spc.flags.SpcFileFeatureEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")

class SpcDataParser {
    List<Double> xArray;
    final FileHeader header;
    final LittleEndianDataInputStream inputStream;

    SpcDataParser(LittleEndianDataInputStream inputStream, FileHeader header) {
        this.inputStream = inputStream;
        this.header = header;
    }

    List<SpcFileSpectrum> parseDataBlock() throws IOException {
        if(header.features().contains(SpcFileFeatureEnum.XY_DIFFERENT_ARRAY)){
            xArray = Utility.readXValuesFromFile(inputStream, header.pointCount());
        } else if (!header.features().contains(SpcFileFeatureEnum.XY)){
            xArray = createEquidistantXValues(header.startingX(), header.endingX(), header.pointCount());
        }

        List<SpcFileSpectrum> dataBlocks = new ArrayList<>();

        for(int i = 0; i < header.subfileCount(); i++){
            SpcFileSpectrum spectrum = parseSpectrum();
            dataBlocks.add(spectrum);
        }

        return dataBlocks;
    }

    private List<Double> createEquidistantXValues(double startingX, double endingX, long pointCount){
        List<Double> x = new ArrayList<>((int) pointCount);
        double step = (endingX - startingX) / (pointCount - 1);
        for (int i = 0; i < pointCount; i++){
            x.add(startingX + i * step);
        }
        return x;
    }

    private SpcFileSpectrum parseSpectrum() throws IOException {
        SpcFileDataSubHeader subHeader = parseSubHeader();

        SpectrumParser spectrumParser = new SpectrumParser(inputStream, header, subHeader, xArray);

        return spectrumParser.parseSpectrum();
    }

    private SpcFileDataSubHeader parseSubHeader() throws IOException {
        inputStream.readUnsignedByte(); // Subflgs - "This value should always be set to null"
        int exponent = inputStream.readByte();
        int index = inputStream.readUnsignedShort();
        double startingZ = inputStream.readFloat();
        double endingZ = inputStream.readFloat();
        inputStream.readFloat(); // Subnois - "This value should always be set to null"
        int pointCount = inputStream.readInt();
        inputStream.readInt(); //Subscan - "This value should be set to null"
        double wAxisValue = inputStream.readFloat();
        Utility.readCharacters(inputStream, 4); //Subresv - "Do not write any data in this area."

        return new SpcFileDataSubHeader(exponent, index, startingZ, endingZ, pointCount, wAxisValue);
    }
}
