package com.elections.counter.document;

import lombok.Getter;

public enum Position {
    ALCALDE("Alcalde"),
    PREFECTO("Prefecto"),
    CONCEJAL_URBANO("Concejal urbano"),
    CONCEJAL_RURAL("Concejal rural");


    @Getter
    private final String label;

    Position(String label) {
        this.label = label;
    }
}
