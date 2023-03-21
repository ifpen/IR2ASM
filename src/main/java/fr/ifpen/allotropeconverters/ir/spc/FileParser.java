package fr.ifpen.allotropeconverters.ir.spc;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@SuppressWarnings({"UnstableApiUsage", "SpellCheckingInspection"}) //LittleEndian is unstable in Guava / Lots of comments with C variable names.
class FileParser {

    private final HeaderParser headerParser = new HeaderParser();
    private final LogParser logParser = new LogParser();

    public SpcFile parseFile(File inputFile) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(new BufferedInputStream(
                new FileInputStream(inputFile)));

        FileHeader header = headerParser.parseHeader(in, Instant.ofEpochMilli(inputFile.lastModified()));

        SpcDataParser dataParser = new SpcDataParser(in, header);

        List<SpcFileSpectrum> spcFileSpectrums =dataParser.parseDataBlock();

        String log = logParser.parseLog(in);

        return new SpcFile(header, spcFileSpectrums, log);
    }







}
