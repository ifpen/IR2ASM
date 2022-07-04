package fr.ifpen.spectrum.ir;

import java.util.List;

public class AllotropeData {
    public List<List<Double>> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<List<Double>> dimensions) {
        this.dimensions = dimensions;
    }

    public List<List<Double>> getMeasures() {
        return measures;
    }

    public void setMeasures(List<List<Double>> measures) {
        this.measures = measures;
    }

    public AllotropeData() {
    }

    private List<List<Double>> dimensions;
    private List<List<Double>> measures;
}
