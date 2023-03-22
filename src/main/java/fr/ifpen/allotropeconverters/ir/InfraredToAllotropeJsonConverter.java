package fr.ifpen.allotropeconverters.ir;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.ifpen.allotropeconverters.ir.schema.FtirEmbedSchema;
import fr.ifpen.allotropeconverters.ir.spc.SpcFileToAllotropeMapper;

import java.io.IOException;
import java.util.List;


/**
 * A converter transforming binary FTIR data into ASM compliant JSON objects
 */
public class InfraredToAllotropeJsonConverter {

    private final SpcFileToAllotropeMapper spcFileToAllotropeMapper = new SpcFileToAllotropeMapper();


    /**
     * Converts a FTIR file
     * @param filePath The path of the file to be converted.
     * @return A list of ASM-compliant JSON objects, representing the data.
     * @throws IOException Thrown in case of an error during parsing.
     */
    public List<ObjectNode> convertFile(String filePath) throws IOException {

        List<FtirEmbedSchema> schemasInFile = spcFileToAllotropeMapper.mapToFtirEmbedSchema(filePath);

        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return schemasInFile.stream().map(schema -> (ObjectNode)mapper.valueToTree(schema)).toList();
    }
}
