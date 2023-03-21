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

public class InfraredToAllotropeJsonConverter {

    private final SpcFileToAllotropeMapper spcFileToAllotropeMapper = new SpcFileToAllotropeMapper();

    public List<ObjectNode> convertFile(String filePath) throws IOException {

        List<FtirEmbedSchema> schemasInFile = spcFileToAllotropeMapper.mapToFtirEmbedSchema(filePath);

        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return schemasInFile.stream().map(schema -> (ObjectNode)mapper.valueToTree(schema)).toList();
    }
}
