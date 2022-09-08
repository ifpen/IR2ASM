package fr.ifpen.spectrum.ir;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.ifpen.spectrum.ir.schema.FtirEmbedSchema;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InfraredToAllotropeJsonConverter {
    /**
     * Converts an SPC IR file to an Allotrope-format compliant JSON.
     * @param args Path to the file to convert.
     */
    public static void main(String[] args){
        try {
            SpcFile spcFile = SpcFileParser.parseFile(args[0]);
            List<FtirEmbedSchema> embedSchemaList = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

            spcFile.dataBlocks.forEach(spcFileSpectrum ->
                embedSchemaList.add(SpcFileToAllotropeMapper.mapToFtirEmbedSchema(spcFile.header, spcFileSpectrum))
            );

            embedSchemaList.forEach(ftirEmbedSchema -> {
                try {
                    mapper.writeValue(new File(args[0].replace(".spc", ".json")), ftirEmbedSchema);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
