package fr.ifpen.spectrum.ir.parser;

import com.google.common.io.LittleEndianDataInputStream;
import fr.ifpen.spectrum.ir.DataPoint;
import fr.ifpen.spectrum.ir.SpcFileDataSubHeader;
import fr.ifpen.spectrum.ir.SpcFileHeader;
import fr.ifpen.spectrum.ir.SpcFileSpectrum;
import fr.ifpen.spectrum.ir.flags.SpcFileFeatureEnum;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")

public class SpcSpectrumParser {
    List<Double> xArray;
    SpcFileHeader header;
    SpcFileDataSubHeader subHeader;
    LittleEndianDataInputStream inputStream;

    public SpcSpectrumParser(
            LittleEndianDataInputStream inputStream,
            SpcFileHeader header,
            SpcFileDataSubHeader subHeader,
            List<Double> xArray) {
        this.inputStream = inputStream;
        this.header = header;
        this.subHeader = subHeader;
        this.xArray = xArray;
    }

    public SpcFileSpectrum parseSpectrum() throws IOException {
        if(header.features.contains(SpcFileFeatureEnum.xyDifferentArray)){
            xArray = Utility.readXValuesFromFile(inputStream, subHeader.pointCount);
        }

        int yExponent = subHeader.exponentY == 0 ? header.exponentY : subHeader.exponentY;

        double yFactor = calculateYFactor(yExponent);

        long pointCount = subHeader.pointCount == 0 ? header.pointCount : subHeader.pointCount;

        List<Double> yArray = readYData((int) pointCount, yFactor, yExponent);

        List<DataPoint> dataPoints = buildDataPoints(xArray, yArray, (int) pointCount);

        return new SpcFileSpectrum(subHeader, dataPoints);
    }

    private double calculateYFactor(long yExponent){
        long offsetExponent = yExponent - (header.features.contains(SpcFileFeatureEnum.y16BitPrecision) ? 16 : 32);

        return Math.pow(2, offsetExponent);
    }

    private List<Double> readYData(int pointCount, double yFactor, long yExponent) throws IOException {
        if (header.features.contains(SpcFileFeatureEnum.y16BitPrecision)){
            return read16BitYData(pointCount, yFactor);
        } else {
            return read32BitYData(pointCount, yFactor, yExponent);
        }
    }

    private List<Double> read16BitYData(int pointCount, double yFactor) throws IOException {
        List<Double> y = new ArrayList<>(pointCount);
        for (int i = 0; i < pointCount; i++){
            y.add(inputStream.readShort() * yFactor);
        }
        return y;
    }

    private List<Double> read32BitYData(int pointCount, double yFactor, long yExponent) throws IOException {
        List<Double> y = new ArrayList<>(pointCount);
        for (int i = 0; i < pointCount; i++){
            if(yExponent != -128){
                y.add(inputStream.readShort() * yFactor);
            } else {
                y.add((double) inputStream.readFloat());
            }
        }
        return y;
    }

    private List<DataPoint> buildDataPoints(List<Double> xArray, List<Double> yArray, int pointCount){
        List<DataPoint> dataPointList = new ArrayList<>(pointCount);

        for(int i = 0; i < pointCount; i++){
            dataPointList.add(new DataPoint(xArray.get(i), yArray.get(i)));
        }

        return dataPointList;
    }
}
