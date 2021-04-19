
package com.gesforback.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.EnumUtils;

public enum GrauParentesco {
    
    ESPOSO("Esposo"),
    ESPOSA("Esposa"),
    FILHO("Filho"),
    FILHA("Filha"),
    NETO("Neto"),
    NETA("Neta"),
    SOBRINHO("Sobrinho"),
    SOBRINHA("Sobrinha"),
    PRIMO("Prima"),
    PRIMA("Prima"),
    AMIGO("Amigo"),
    AMIGA("Amiga");
    
    private String value;

    private GrauParentesco(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @JsonCreator
    public static GrauParentesco forValue(String name) {
        return EnumUtils.getEnumIgnoreCase(GrauParentesco.class, name);
    }
    
    
}
