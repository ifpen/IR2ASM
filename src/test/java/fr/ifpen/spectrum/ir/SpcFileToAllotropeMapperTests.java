package fr.ifpen.spectrum.ir;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import fr.ifpen.spectrum.ir.schema.FtirEmbedSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpcFileToAllotropeMapperTests {

    private SpcFile file;

    @BeforeAll
    void setFilePath(){

        File testFile = new File("src/test/resources/201bk01.spc");

        try {
            file = SpcFileParser.parseFile(testFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void mappingWorks() {
        FtirEmbedSchema embedSchema = SpcFileToAllotropeMapper.mapToFtirEmbedSchema(
                file.dataBlocks().get(0));

        JsonSchema referenceSchema = getJsonSchemaFromClasspath("ftir.embed.schema.json");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.valueToTree(embedSchema);

        Set<ValidationMessage> errors = referenceSchema.validate(node);
        Assertions.assertTrue(errors.isEmpty());
    }

    private JsonSchema getJsonSchemaFromClasspath(String name) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);
        return factory.getSchema(is);
    }
}
