package fr.ifpen.allotropeconverters.ir.spc;

record SpcFileDataSubHeader(
        int exponentY,
        int index,
        double startingZ,
        double endingZ,
        int pointCount,
        double wAxisValue) {
}
