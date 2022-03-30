package fr.ifpen.spectrum.ir;

public class SpcFile {
    private SpcFileVersion fileVersion;
    private SpcFileDataType dataType;

    public SpcFileVersion getFileVersion(){
        return this.fileVersion;
    }

    public void setFileVersion(SpcFileVersion fileVersion){
        this.fileVersion = fileVersion;
    }

    public SpcFileDataType getDataType(){
        return this.dataType;
    }

    public void setFileDataType(SpcFileDataType dataType){
        this.dataType = dataType;
    }
}
