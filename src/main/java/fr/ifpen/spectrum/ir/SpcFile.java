package fr.ifpen.spectrum.ir;

import java.util.List;

public record SpcFile(SpcFileHeader header,
                      List<SpcFileSpectrum> dataBlocks) {
}
