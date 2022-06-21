package fr.ifpen.spectrum.ir;

import java.util.ArrayList;
import java.util.List;

public class AllotropeData {
    public List<List<Double>> getDimensions() {
        return Dimensions;
    }

    public void setDimensions(List<List<Double>> dimensions) {
        Dimensions = dimensions;
    }

    public List<List<Double>> getMeasures() {
        return Measures;
    }

    public void setMeasures(List<List<Double>> measures) {
        Measures = measures;
    }

    public AllotropeData() {
    }

    private List<List<Double>> Dimensions;
    private List<List<Double>> Measures;
}
