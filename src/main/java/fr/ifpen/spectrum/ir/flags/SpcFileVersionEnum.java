package fr.ifpen.spectrum.ir.flags;

public enum SpcFileVersionEnum implements  FlagEnumInterface {
    NEW_FORMAT(0x4B),
    OLD_FORMAT(0x4D),
    BAD_FORMAT(0);

    private final int versionFlag;

    SpcFileVersionEnum(int versionFlag) {
        this.versionFlag = versionFlag;
    }

    @Override
    public long getFlagValue() {
        return this.versionFlag;
    }
}
