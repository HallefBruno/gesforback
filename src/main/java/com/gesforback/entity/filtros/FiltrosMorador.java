
package com.gesforback.entity.filtros;

public class FiltrosMorador {
    
    private String nome;
    private String cpf;
    private String residencia;
    private String telefone;
    private boolean isProprietario;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isIsProprietario() {
        return isProprietario;
    }

    public void setIsProprietario(boolean isProprietario) {
        this.isProprietario = isProprietario;
    }
}
