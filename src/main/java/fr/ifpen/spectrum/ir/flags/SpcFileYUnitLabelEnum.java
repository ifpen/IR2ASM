package fr.ifpen.spectrum.ir.flags;

public enum SpcFileYUnitLabelEnum implements  FlagEnumInterface{
    YARB(0, "Arbitrary Intensity"),
    YIGRAM(1, "Interferogram"),
    YABSRB(2,"Absorbance"),
    YKMONK(3, "Kubelka-Monk"),
    YCOUNT(4,"Counts"),
    YVOLTS(5,"Volts"),
    YDEGRS(6,"Degrees"),
    YAMPS(7,"Milliamps"),
    YMETERS(8,"Millimeters"),
    YMVOLTS(9, "Millivolts"),
    YLOGDR(10, "Log(1/R)"),
    YPERCNT(11, "Percent"),
    YINTENS(12, "Intensity"),
    YRELINT(13,"Relative Intensity"),
    YENERGY(14,"Energy"),
    //15 unused
    YDECBL(16,"Decibel"),
    //17 unused
    //18 unused
    YDEGRF(19,"Temperature (F)"),
    YDEGRC(20,"Temperature (C)"),
    YDEGRK(21,"Temperature (K)"),
    YINDRF(22,"Index of Refraction [N]"),
    YEXTCF(23, "Extinction Coeff. [K]"),
    YREAL(24, "Real"),
    YIMAG(25, "Imaginary"),
    YCMPLX(26, "Complex"),

    YTRANS(128, "Transmission (ALL HIGHER MUST HAVE VALLEYS!)"),
    YREFLEC(129, "Reflectance"),
    YVALLEY(130, "Arbitrary or Single Beam with Valley Peaks"),
    YEMISN(131, "Emission");


    private final int flagValue;
    private final String description;

    SpcFileYUnitLabelEnum(int flagValue, String description) {

        this.flagValue = flagValue;
        this.description = description;
    }

    @Override
    public long getFlagValue() {
        return this.flagValue;
    }
}
