package fr.ifpen.allotropeconverters.ir;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InfraredToAllotropeJsonConverterTests {
    private String filePath;

    @BeforeAll
    void setFilePath(){
        filePath = Paths.get(new File("src/test/resources/201bk01.spc").toURI()).toString();
    }

    @Test
    void IntegrationTest() throws IOException {
        InfraredToAllotropeJsonConverter converter = new InfraredToAllotropeJsonConverter();

        converter.convertFile(filePath);
    }
}
