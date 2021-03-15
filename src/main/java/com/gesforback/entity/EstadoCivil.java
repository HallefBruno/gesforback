
package com.gesforback.entity;

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
}
