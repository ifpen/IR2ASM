package fr.ifpen.spectrum.ir.parser;

import com.google.common.io.LittleEndianDataInputStream;
import fr.ifpen.spectrum.ir.SpcFileDataSubHeader;
import fr.ifpen.spectrum.ir.SpcFileHeader;
import fr.ifpen.spectrum.ir.SpcFileSpectrum;
import fr.ifpen.spectrum.ir.flags.SpcFileFeatureEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fr.ifpen.spectrum.ir.parser.Utility.readCharacters;

@SuppressWarnings("UnstableApiUsage")

public class SpcDataParser {
    List<Double> xArray;
    SpcFileHeader header;
    LittleEndianDataInputStream inputStream;

    public SpcDataParser(LittleEndianDataInputStream inputStream, SpcFileHeader header) {
        this.inputStream = inputStream;
        this.header = header;
    }

    public List<SpcFileSpectrum> parseDataBlock() throws IOException {
        if(header.features.contains(SpcFileFeatureEnum.xyDifferentArray)){
            xArray = Utility.readXValuesFromFile(inputStream, header.pointCount);
        } else if (!header.features.contains(SpcFileFeatureEnum.xy)){
            xArray = createEquidistantXValues(header.startingX, header.endingX, header.pointCount);
        }

        List<SpcFileSpectrum> dataBlocks = new ArrayList<>();

        for(int i = 0; i < header.subfileCount; i++){
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

        SpcSpectrumParser spectrumParser = new SpcSpectrumParser(inputStream, header, subHeader, xArray);

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
        readCharacters(inputStream, 4); //Subresv - "Do not write any data in this area."

        return new SpcFileDataSubHeader(exponent, index, startingZ, endingZ, pointCount, wAxisValue);
    }
}
