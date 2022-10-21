package fr.ifpen.spectrum.ir;

import fr.ifpen.spectrum.ir.flags.*;

import java.time.Instant;
import java.util.Set;

public record SpcFileHeader(SpcFileVersionEnum fileVersion,
                            Set<SpcFileFeatureEnum> features,
                            SpcExperimentTypeEnum experimentType,
                            int exponentY,
                            long pointCount,
                            double startingX,
                            double endingX,
                            int subfileCount,
                            SpcFileXUnitLabelEnum xUnitLabel,
                            SpcFileYUnitLabelEnum yUnitLabel,
                            SpcFileXUnitLabelEnum zUnitLabel,
                            Instant date,
                            String resolutionDescription,
                            String sourceInstrument,
                            int peakPointNumber,
                            String memo,
                            int offsetToLog,
                            String xyzLabels) {
}
