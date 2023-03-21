package fr.ifpen.allotropeconverters.ir.spc;

import java.time.Instant;

public record SpcFileMetadata(String path, Instant lastModifiedDate) {
}
