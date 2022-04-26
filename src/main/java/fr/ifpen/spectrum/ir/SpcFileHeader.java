package fr.ifpen.spectrum.ir;

import fr.ifpen.spectrum.ir.flags.*;

import java.util.EnumSet;

public class SpcFileHeader {
    public final SpcFileVersionEnum fileVersion;
    public final EnumSet<SpcFileFeatureEnum> features;
    public final SpcExperimentTypeEnum experimentType;
    public final int exponentY;
    public final long pointCount;
    public final double startingX;
    public final double endingX;
    public final int subfileCount;
    public final SpcFileXUnitLabelEnum xUnitLabel;
    public final SpcFileYUnitLabelEnum yUnitLabel;
    public final SpcFileXUnitLabelEnum zUnitLabel;
    public final String resolutionDescription;
    public final String sourceInstrument;
    public final int peakPointNumber;
    public final String memo;
    public final int offsetToLog;
    public final String XYZLabels;

    public SpcFileHeader(
            SpcFileVersionEnum fileVersion,
            EnumSet<SpcFileFeatureEnum> features,
            SpcExperimentTypeEnum experimentType,
            int exponentY,
            long pointCount,
            double startingX,
            double endingX,
            int subfileCount,
            SpcFileXUnitLabelEnum xUnitLabel,
            SpcFileYUnitLabelEnum yUnitLabel,
            SpcFileXUnitLabelEnum zUnitLabel,
            String resolutionDescription,
            String sourceInstrument,
            int peakPointNumber,
            String memo,
            int offsetToLog,
            String xyzLabels) {
        this.fileVersion = fileVersion;
        this.features = features;
        this.experimentType = experimentType;
        this.exponentY = exponentY;
        this.pointCount = pointCount;
        this.startingX = startingX;
        this.endingX = endingX;
        this.subfileCount = subfileCount;
        this.xUnitLabel = xUnitLabel;
        this.yUnitLabel = yUnitLabel;
        this.zUnitLabel = zUnitLabel;
        this.resolutionDescription = resolutionDescription;
        this.sourceInstrument = sourceInstrument;
        this.peakPointNumber = peakPointNumber;
        this.memo = memo;
        this.offsetToLog = offsetToLog;
        XYZLabels = xyzLabels;
    }
}
