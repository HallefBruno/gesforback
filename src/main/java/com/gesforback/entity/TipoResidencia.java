
package com.gesforback.entity;

public enum TipoResidencia {
    
    APARTAMENTO("Apartamento"),
    CASA("Casa"),
    CONJUNTO_APARTAMENTO("Conjunto de apartamento"),
    CONJUNTO_HABITACIONAL_BLOCO("Conjuto habitacional bloco"),
    CONJUNTO_HABITACIONAL_CASA("Conjunto habitacional casa");
    
    private final String descricao;

    private TipoResidencia(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return this.descricao;
    }
    
}
