package fr.ifpen.spectrum.ir;

public enum SpcFileDataType {
    /**
     * Single File, Evenly Spaced X Values
     */
    SINGLE_EVENLY_SPACED,
    /**
     * Multifile, Evenly Spaced X Values
     */
    MULTI_EVENLY_SPACED,
    /**
     * Single File, Unevenly Spaced X values
     */
    SINGLE_UNEVENLY_SPACED,
    /**
     * Multifile, Unevenly Spaced X Values, Common X Array
     */
    MULTI_UNEVENLY_SPACED_COMMON_X,
    /**
     * Multifile, Unevenly Spaced X Values, Unique X Arrays
     */
    MULTI_UNEVENLY_SPACED_UNIQUE_X
}
