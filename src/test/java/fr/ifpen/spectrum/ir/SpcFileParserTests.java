package fr.ifpen.spectrum.ir;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

class SpcFileParserTests {

    @Test
    void checkFileFormat(){
        SpcFile file = null;
        try {
            file = SpcFileParser.parseFile("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(SpcFileVersion.LSB, file.getFileVersion());
    }

    @Test
    void checkDataType(){
        SpcFile file = null;
        try {
            file = SpcFileParser.parseFile("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(SpcFileDataType.SINGLE_EVENLY_SPACED, file.getDataType());
    }
}