package fr.ifpen.spectrum.ir;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InfraredToAllotropeJsonConverterTests {
    private String filePath;

    @BeforeAll
    void setFilePath(){
        filePath = Paths.get(new File("src/test/resources/201bk01.spc").toURI()).toString();
    }

    @Test
    void IntegrationTest(){
        String[] args = {filePath};
        InfraredToAllotropeJsonConverter.main(args);
        File resultFile = new File("src/test/resources/201bk01.json");
        Assertions.assertTrue(resultFile.exists());
    }
}
