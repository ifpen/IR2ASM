package fr.ifpen.spectrum.ir;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import fr.ifpen.spectrum.ir.schema.FtirEmbedSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpcFileToAllotropeMapperTests {

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
    void mappingWorks() throws IOException {
        FtirEmbedSchema embedSchema = SpcFileToAllotropeMapper.mapToFtirEmbedSchema(
                file.header,
                file.dataBlocks.get(0));

        JsonSchema referenceSchema = getJsonSchemaFromClasspath("ftir.embed.schema.json");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.valueToTree(embedSchema);

        Set<ValidationMessage> errors = referenceSchema.validate(node);
        Assertions.assertEquals(errors.size(),0);
    }

    private JsonSchema getJsonSchemaFromClasspath(String name) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);
        return factory.getSchema(is);
    }
}
