package fr.ifpen.allotropeconverters.ir.spc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import fr.ifpen.allotropeconverters.ir.schema.FtirEmbedSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpcFileToAllotropeMapperTests {

    private final String testFilePath = "src/test/resources/201bk01.spc";

    @Test
    void mappingWorks() throws IOException {

        SpcFileToAllotropeMapper spcFileToAllotropeMapper = new SpcFileToAllotropeMapper();

        FtirEmbedSchema embedSchema = spcFileToAllotropeMapper.mapToFtirEmbedSchema(testFilePath).get(0);

        JsonSchema referenceSchema = getJsonSchemaFromClasspath("ftir.embed.schema.json");

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
