package fr.ifpen.spectrum.ir.flags;

public enum SpcExperimentTypeEnum implements FlagEnumInterface {

    GENERAL (0), /* General SPC (could be anything) */
    GC(1), /* Gas Chromatogram */
    CGM(2), /* General Chromatogram (same as SPCGEN with TCGRAM) */
    HPLC(3), /* HPLC Chromatogram */
    FTIR(4), /* FT-IR, FT-NIR, FT-Raman Spectrum or Igram (Can also be used for scanning IR.) */
    NIR(5), /* NIR Spectrum (Usually multi-spectral data sets for calibration.) */
    UV(7), /* UV-VIS Spectrum (Can be used for single scanning UV-VIS-NIR) */
    XRY(8), /* X-ray Diffraction Spectrum */
    MS(9), /* Mass Spectrum (Can be single, GC-MS, Continuum, Centroid or TOF.) */
    NMR(10), /* NMR Spectrum or FID */
    RMN(11), /* Raman Spectrum (Usually Diode Array, CCD, etc. use SPCFTIR for FT-Raman.) */
    FLR(12), /* Fluorescence Spectrum */
    ATM(13), /* Atomic Spectrum */
    DAD(14), /* Chromatography Diode Array Spectra */
    BAD_FORMAT(-1);


    private final int flagValue;

    SpcExperimentTypeEnum(int flagValue) {
        this.flagValue = flagValue;
    }

    @Override
    public long getFlagValue() {
        return this.flagValue;
    }
}
