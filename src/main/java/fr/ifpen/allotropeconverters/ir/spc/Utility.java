package fr.ifpen.allotropeconverters.ir.spc;

import com.google.common.io.LittleEndianDataInputStream;
import fr.ifpen.allotropeconverters.ir.spc.flags.FlagEnumInterface;
import fr.ifpen.allotropeconverters.ir.spc.flags.SpcFileFeatureEnum;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")

class Utility {

    private Utility(){}

    static String readCharacters(DataInput in, int numberOfCharacters) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < numberOfCharacters; i++ ){
            byte b = in.readByte();
            char c = (char) (b & 0xFF); //https://stackoverflow.com/questions/17912640/byte-and-char-conversion-in-java
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    static Set<SpcFileFeatureEnum> getDataFlags(long fileFlagValue) {
        EnumSet<SpcFileFeatureEnum> dataFlags = EnumSet.noneOf(SpcFileFeatureEnum.class);
        for (SpcFileFeatureEnum featureFlag : SpcFileFeatureEnum.values()) {
            long flagValue = featureFlag.getFlagValue();
            if ((flagValue & fileFlagValue) == flagValue){
                dataFlags.add(featureFlag);
            }
        }
        return dataFlags;
    }

    static <T extends FlagEnumInterface> T getEnumFromFlag(int flagValue, Class<T> flagEnum)
            throws IllegalArgumentException {
        for (T flag : flagEnum.getEnumConstants()) {
            if (flag.getFlagValue() == flagValue) {
                return flag;
            }
        }
        throw new IllegalArgumentException("No corresponding flag");
    }

    static List<Double> readXValuesFromFile(LittleEndianDataInputStream inputStream, long pointCount)
            throws IOException {
        ArrayList<Double> x = new ArrayList<>((int) pointCount);
        for(int i = 0; i < pointCount; i++){
            x.add((double) inputStream.readFloat());
        }
        return x;
    }

}
