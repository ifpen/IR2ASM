package fr.ifpen.allotropeconverters.ir.spc;

import java.io.DataInput;
import java.io.IOException;

class LogParser {
    String parseLog(DataInput inputStream) throws IOException {
        LogHeader header = readHeader(inputStream);
        Utility.readCharacters(inputStream, (int) header.binaryBlockSize()); //Discard binary block

        //Total size - header - diskArea - binary = text block
        long textLogsize = header.logSize() - 64 - header.diskAreaSize() - header.binaryBlockSize();

        return Utility.readCharacters(inputStream, (int)textLogsize);
    }

    private LogHeader readHeader(DataInput input) throws IOException {
        return new LogHeader(
                input.readInt(),
                input.readInt(),
                input.readInt(),
                input.readInt(),
                input.readInt(),
                Utility.readCharacters(input,44)
        );
    }
}
