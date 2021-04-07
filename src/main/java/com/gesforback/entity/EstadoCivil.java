package com.gesforback.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.EnumUtils;

public enum EstadoCivil {

    CASADO("Casado (a)"),
    SOLTEIRO("Solteiro (a)"),
    DIVORCIADO("Divorciado (a)"),
    VIUVO("Viúvo (a)");

    private final String descricao;

    private EstadoCivil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    @JsonCreator
    public static EstadoCivil forValue(String name) {
        return EnumUtils.getEnumIgnoreCase(EstadoCivil.class, name);
    }
}
