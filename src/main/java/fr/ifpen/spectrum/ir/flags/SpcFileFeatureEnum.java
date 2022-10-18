package fr.ifpen.spectrum.ir.flags;

public enum SpcFileFeatureEnum implements FlagEnumInterface {
        Y_16_BIT_PRECISION(1), //Y data is stored in 16-bit precision (instead of 32-bit)
        USE_EXPERIMENT_EXTENSION(1 << 1), // Use Experiment extension, not SPC
        MULTI_FILE(1 << 2), // Multifile
        Z_VALUES_RANDOM(1 << 3), // If a Multifile, Z values are randomly ordered
        Z_VALUES_UNEVEN(1 << 4), // If a Multifile, Z values are ordered, but not even
        CUSTOM_AXIS_LABEL(1 << 5), //Use custom axis labels (obsolete)
        XY_DIFFERENT_ARRAY(1 << 6), // If an XY file and a Multifile, each subfile has its own X array
        XY(1 << 7); //XY file

        private final long featureFlagValue;

        SpcFileFeatureEnum(long featureFlagValue) {
            this.featureFlagValue = featureFlagValue;
        }

        @Override
        public long getFlagValue() {
                return this.featureFlagValue;
        }
}
