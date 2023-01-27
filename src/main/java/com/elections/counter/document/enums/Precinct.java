package com.elections.counter.document.enums;

import lombok.Getter;

public enum Precinct {
    UNIDAD_EDUCATIVA_BUENOS_AIRES("Unidad Educativa Buenos Aires"),
    ESCUELA_FRANKLIN_ROOSEVELT("Escuela de Educación Basica Franklin Roosevelt"),
    ESCUELA_PALMIRA_TOCTEMI("Escuela y Casa Comunal San Francisco Palmira de Toctemi - Awa"),
    UNIDAD_EDUCATIVA_CAHUASQUI("Unidad Educativa Cahuasqui"),
    UNIDAD_EDUCATIVA_YACHAY("Unidad Educativa del Milenio Yachay / Ex Escuela Abdon Calderon"),
    UNIDAD_EDUCATIVA_ELOY_ALFARO("Unidad Educativa Eloy Alfaro"),
    UNIDAD_EDUCATIVA_PABLO_ARENAS
            ("Unidad Edcucativa Cahuasqui Bloque Pablo Arenas / Escuela 5 de Junio"),
    UNIDAD_EDUCATIVA_ROCAFUERTE("Unidad Educativa Vicente Rocafuerte"),
    ESCUELA_HERNAN_CORTEZ("Antigua Escuela Hernán Cortez"),
    UNIDAD_EDUCATIVA_URCUQUI("Unidad Educativa Urcuquí");

    @Getter
    private final String label;

    Precinct(String label) {
        this.label = label;
    }
}
