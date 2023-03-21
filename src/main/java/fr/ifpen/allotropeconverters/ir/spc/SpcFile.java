package fr.ifpen.allotropeconverters.ir.spc;

import java.util.List;

record SpcFile(FileHeader header,
               List<SpcFileSpectrum> dataBlocks,
               String log) {
}
