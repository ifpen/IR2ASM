package fr.ifpen.allotropeconverters.ir.spc;

import fr.ifpen.allotropeconverters.ir.spc.flags.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.EnumSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileParserTests {

    private SpcFile file;

    @BeforeAll
    void setFilePath(){
        File testFile = new File("src/test/resources/201bk01.spc");

        FileParser parser = new FileParser();

        try {
            file = parser.parseFile(testFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkHeaderParsing(){
        Assertions.assertEquals(SpcFileVersionEnum.NEW_FORMAT, file.header().fileVersion());
        Assertions.assertEquals(EnumSet.noneOf(SpcFileFeatureEnum.class), file.header().features());
        Assertions.assertEquals(SpcExperimentTypeEnum.FTIR, file.header().experimentType());
        Assertions.assertEquals(-128, file.header().exponentY());
        Assertions.assertEquals(8192, file.header().pointCount());
        Assertions.assertEquals(1, file.header().subfileCount());
        Assertions.assertEquals(SpcFileXUnitLabelEnum.XWAVEN, file.header().xUnitLabel());
        Assertions.assertEquals(SpcFileYUnitLabelEnum.YENERGY, file.header().yUnitLabel());
        Assertions.assertEquals(SpcFileXUnitLabelEnum.XARB, file.header().zUnitLabel());
        Assertions.assertEquals(-128, file.dataBlocks().get(0).subHeader().exponentY());
    }


    @Test
    void checkSubHeaderIndex(){
        Assertions.assertEquals(-128, file.dataBlocks().get(0).subHeader().exponentY());
        Assertions.assertEquals(0, file.dataBlocks().get(0).subHeader().index());
        Assertions.assertEquals(8192, file.dataBlocks().get(0).subHeader().pointCount());
    }

    @Test
    void checkValues(){
        //Reference: Matlab function GSSpcRead
        Assertions.assertEquals(0.0168, file.dataBlocks().get(0).dataPoints().get(0).y(), 0.01);
        Assertions.assertEquals(0, file.dataBlocks().get(0).dataPoints().get(0).x(), 0.01);
        Assertions.assertEquals(0.0131, file.dataBlocks().get(0).dataPoints().get(1).y(), 0.01);
        Assertions.assertEquals(1.9287, file.dataBlocks().get(0).dataPoints().get(1).x(), 0.01);
    }

    @Test
    void checkLog(){
        Assertions.assertEquals("Legacy", file.log().substring(0,6));
    }
}