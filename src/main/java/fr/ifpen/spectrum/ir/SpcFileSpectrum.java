package fr.ifpen.spectrum.ir;

import java.util.List;

public record SpcFileSpectrum(
        SpcFileDataSubHeader subHeader,
        List<DataPoint> dataPoints) {
}
