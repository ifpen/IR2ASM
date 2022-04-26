package fr.ifpen.spectrum.ir;

import java.util.List;

public class SpcFile {
    public final SpcFileHeader header;
    public final List<SpcFileSpectrum> dataBlocks;


    public SpcFile(SpcFileHeader header, List<SpcFileSpectrum> dataBlocks) {
        this.header = header;
        this.dataBlocks = dataBlocks;
    }
}
