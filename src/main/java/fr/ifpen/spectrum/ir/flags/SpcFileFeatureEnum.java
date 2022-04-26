package fr.ifpen.spectrum.ir.flags;

public enum SpcFileFeatureEnum implements FlagEnumInterface {
        y16BitPrecision(1), //Y data is stored in 16-bit precision (instead of 32-bit)
        useExperimentExtension(1 << 1), // Use Experiment extension, not SPC
        multiFile(1 << 2), // Multifile
        zValuesRandom(1 << 3), // If a Multifile, Z values are randomly ordered
        ZValuesUneven(1 << 4), // If a Multifile, Z values are ordered, but not even
        customAxisLabel(1 << 5), //Use custom axis labels (obsolete)
        xyDifferentArray(1 << 6), // If an XY file and a Multifile, each subfile has its own X array
        xy(1 << 7); //XY file

        private final long featureFlagValue;

        SpcFileFeatureEnum(long featureFlagValue) {
            this.featureFlagValue = featureFlagValue;
        }

        @Override
        public long GetFlagValue() {
                return this.featureFlagValue;
        }
}
