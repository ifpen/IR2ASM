package fr.ifpen.allotropeconverters.ir.spc;

import java.util.List;

record SpcFileSpectrum(
        SpcFileDataSubHeader subHeader,
        List<DataPoint> dataPoints) {
}
