package fr.ifpen.spectrum.ir;

import java.util.List;

public class SpcFileSpectrum {
    public final SpcFileDataSubHeader subHeader;
    public final List<DataPoint> dataPoints;

    public SpcFileSpectrum(SpcFileDataSubHeader subHeader, List<DataPoint> dataPoints) {
        this.subHeader = subHeader;

        this.dataPoints = dataPoints;
    }
}
