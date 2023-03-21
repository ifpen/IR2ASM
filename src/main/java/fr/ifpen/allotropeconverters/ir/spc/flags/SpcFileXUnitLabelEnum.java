package fr.ifpen.allotropeconverters.ir.spc.flags;

public enum SpcFileXUnitLabelEnum implements  FlagEnumInterface{
    XARB(0, "Arbitrary"),
    XWAVEN(1, "Wavenumber (cm-1)"),
    XUMETR(2,"Micrometers"),
    XNMETR(3, "Nanometers"),
    XSECS(4,"Seconds"),
    XMINUTS(5,"Minutes"),
    XHERTZ(6,"Hertz"),
    XKHERTZ(7,"Kilohertz"),
    XMHERTZ(8,"Megahertz"),
    XMUNITS(9, "Mass (M/z)"),
    XPPM(10, "Parts per million"),
    XDAYS(11, "Days"),
    XYEARS(12, "Years"),
    XRAMANS(13,"Raman Shift (cm-1)"),
    XEV(14,"Electron Volts (eV)"),
    XTEXTL(15,"X,Y,Z text labels in fcatxt (old 4Dh version only)"),
    XDIODE(16,"Diode Number"),
    XCHANL(17, "Channel"),
    XDEGRS(18,"Degrees"),
    XDEGRF(19,"Temperature (F)"),
    XDEGRC(20,"Temperature (C)"),
    XDEGRK(21,"Temperature (K)"),
    XPOINT(22,"Data Points"),
    XMSEC(23, "Milliseconds (mSec)"),
    XUSEC(24, "Microseconds (uSec)"),
    XNSEC(25, "Nanoseconds (nSec)"),
    XGHERTZ(26, "Gigahertz (GHz)"),
    XCM(27, "Centimeters (cm)"),
    XMETERS(28, "Meters (m)"),
    XMMETR(29, "Millimeters (mm)"),
    XHOURS(30, "Hours"),
    XDBLIGM(255, "Double interferogram (no display labels)");

    private final int flagValue;
    private final String description;

    SpcFileXUnitLabelEnum(int flagValue, String description) {

        this.flagValue = flagValue;
        this.description = description;
    }

    @Override
    public long getFlagValue() {
        return this.flagValue;
    }
}
