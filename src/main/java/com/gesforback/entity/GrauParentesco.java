
package com.gesforback.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.EnumUtils;

public enum GrauParentesco {
    
    ESPOSO("Esposo"),
    ESPOSA("Esposa"),
    PAI("Pai"),
    MAE("Mãe"),
    AVÔ("Avô"),
    AVÓ("Avó"),
    FILHO("Filho"),
    FILHA("Filha"),
    NETO("Neto"),
    NETA("Neta"),
    BISNETO("Bisneto"),
    BISNETA("Bisneta"),
    SOBRINHO("Sobrinho"),
    SOBRINHA("Sobrinha"),
    PRIMO("Primo"),
    PRIMA("Prima"),
    GENRO("Genro"),
    NORA("Nora"),
    AMIGO("Amigo"),
    AMIGA("Amiga");
    
    private String descricao;

    private GrauParentesco(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return this.descricao;
    }
    
    @JsonCreator
    public static GrauParentesco forValue(String name) {
        return EnumUtils.getEnumIgnoreCase(GrauParentesco.class, name);
    }
    
    
}
