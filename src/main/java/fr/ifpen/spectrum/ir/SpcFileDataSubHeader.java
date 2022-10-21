package fr.ifpen.spectrum.ir;

public record SpcFileDataSubHeader(
        int exponentY,
        int index,
        double startingZ,
        double endingZ,
        int pointCount,
        double wAxisValue) {
}
