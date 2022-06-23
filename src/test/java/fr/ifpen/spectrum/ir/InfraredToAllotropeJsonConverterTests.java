package fr.ifpen.spectrum.ir;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InfraredToAllotropeJsonConverterTests {
    private String filePath;

    @BeforeAll
    void setFilePath(){
        URI uri = null;
        try {
            uri = SpcFileParserTests.class.getClassLoader().getResource("201bk01.spc").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        filePath = Paths.get(uri).toString();
    }

    @Test
    void IntegrationTest(){
        String[] args = {filePath};
        InfraredToAllotropeJsonConverter.main(args);
    }
}
