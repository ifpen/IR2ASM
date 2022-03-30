package fr.ifpen.spectrum.ir;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class SpcFileParser {
    public static SpcFile parseFile(String filePath) throws IOException {
        byte[] fileContent = readFile(filePath);
        SpcFile file = new SpcFile();
        file.setFileVersion(SpcFileVersion.LSB);
        file.setFileDataType(SpcFileDataType.SINGLE_EVENLY_SPACED);
        return file;
    }

    private static byte[] readFile(String filePath) throws IOException {
        Path path = FileSystems.getDefault().getPath("",filePath);
        return Files.readAllBytes(path);
    }
}
