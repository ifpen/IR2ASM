package fr.ifpen.allotropeconverters.ir.spc;

record LogHeader(
        long logSize,
        long memorySize,
        long textOffset,
        long binaryBlockSize,
        long diskAreaSize,
        String reservedBlock){}

