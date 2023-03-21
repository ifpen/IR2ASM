package fr.ifpen.allotropeconverters.ir.spc;

import fr.ifpen.allotropeconverters.ir.spc.flags.*;

import java.time.Instant;
import java.util.Set;

record FileHeader(SpcFileVersionEnum fileVersion,
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
