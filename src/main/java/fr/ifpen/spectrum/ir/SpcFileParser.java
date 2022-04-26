package fr.ifpen.spectrum.ir;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.ifpen.spectrum.ir.flags.*;
import fr.ifpen.spectrum.ir.parser.SpcDataParser;
import fr.ifpen.spectrum.ir.parser.SpcHeaderParser;

@SuppressWarnings({"UnstableApiUsage", "SpellCheckingInspection"}) //LittleEndian is unstable in Guava / Lots of comments with C variable names.
public class SpcFileParser {
    public static SpcFile parseFile(String filePath) throws Exception {
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(new BufferedInputStream(
                new FileInputStream(filePath)));

        SpcFileHeader header = SpcHeaderParser.parseHeader(in);

        SpcDataParser dataParser = new SpcDataParser(in, header);

        List<SpcFileSpectrum> spcFileSpectrums =dataParser.parseDataBlock();

        return new SpcFile(header, spcFileSpectrums);
    }







}
