package com.elections.counter.document;

import lombok.Getter;

public enum Parish {
    URCUQUI("Urcuquí"),
    PABLO_ARENAS("Pablo Arenas"),
    CAHUASQUI("Cahuasquí"),
    LA_MERCED_DE_BUENOS_AIRES("La Merced de Buenos Aires"),
    SAN_BLAS("San Blass"),
    TUMBABIRO("Tumbabiro");

    @Getter
    private final String label;

    Parish(String label) {
        this.label = label;
    }
}
