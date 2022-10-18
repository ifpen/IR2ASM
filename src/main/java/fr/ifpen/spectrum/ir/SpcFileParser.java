package fr.ifpen.spectrum.ir;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

import fr.ifpen.spectrum.ir.parser.SpcDataParser;
import fr.ifpen.spectrum.ir.parser.SpcHeaderParser;

@SuppressWarnings({"UnstableApiUsage", "SpellCheckingInspection"}) //LittleEndian is unstable in Guava / Lots of comments with C variable names.
public class SpcFileParser {

    private SpcFileParser(){}

    public static SpcFile parseFile(File inputFile) throws IOException {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(new BufferedInputStream(
                new FileInputStream(inputFile)));

        SpcFileHeader header = SpcHeaderParser.parseHeader(in, Instant.ofEpochMilli(inputFile.lastModified()));

        SpcDataParser dataParser = new SpcDataParser(in, header);

        List<SpcFileSpectrum> spcFileSpectrums =dataParser.parseDataBlock();

        return new SpcFile(header, spcFileSpectrums);
    }







}
