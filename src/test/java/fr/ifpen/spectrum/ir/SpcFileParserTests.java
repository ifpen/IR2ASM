package fr.ifpen.spectrum.ir;

import fr.ifpen.spectrum.ir.flags.*;
import org.junit.jupiter.api.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.EnumSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpcFileParserTests {

    private String filePath;
    private SpcFile file;

    @BeforeAll
    void setFilePath(){
        URI uri = null;
        try {
            uri = SpcFileParserTests.class.getClassLoader().getResource("201bk01.spc").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        filePath = Paths.get(uri).toString();

        try {
            file = SpcFileParser.parseFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkHeaderParsing(){
        Assertions.assertEquals(SpcFileVersionEnum.NEW_FORMAT, file.header.fileVersion);
        Assertions.assertEquals(EnumSet.noneOf(SpcFileFeatureEnum.class), file.header.features);
        Assertions.assertEquals(SpcExperimentTypeEnum.FTIR, file.header.experimentType);
        Assertions.assertEquals(-128, file.header.exponentY);
        Assertions.assertEquals(8192, file.header.pointCount);
        Assertions.assertEquals(1, file.header.subfileCount);
        Assertions.assertEquals(SpcFileXUnitLabelEnum.XWAVEN, file.header.xUnitLabel);
        Assertions.assertEquals(SpcFileYUnitLabelEnum.YENERGY, file.header.yUnitLabel);
        Assertions.assertEquals(SpcFileXUnitLabelEnum.XARB, file.header.zUnitLabel);
        Assertions.assertEquals(-128, file.dataBlocks.get(0).subHeader.exponentY);
    }


    @Test
    void checkSubHeaderIndex(){
        Assertions.assertEquals(-128, file.dataBlocks.get(0).subHeader.exponentY);
        Assertions.assertEquals(0, file.dataBlocks.get(0).subHeader.index);
        Assertions.assertEquals(8192, file.dataBlocks.get(0).subHeader.pointCount);
    }
}