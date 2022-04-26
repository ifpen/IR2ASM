package fr.ifpen.spectrum.ir;

public class SpcFileDataSubHeader {
    public final int exponentY;
    public final int index;
    public final double startingZ;
    public final double endingZ;
    public final int pointCount;
    public final double wAxisValue;

    public SpcFileDataSubHeader(
            int exponentY,
            int index,
            double startingZ,
            double endingZ,
            int pointCount,
            double wAxisValue
    ) {
        this.exponentY = exponentY;
        this.index = index;
        this.startingZ = startingZ;
        this.endingZ = endingZ;
        this.pointCount = pointCount;
        this.wAxisValue = wAxisValue;
    }
}
