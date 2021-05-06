
package com.gesforback.entity.dto;

/**
 *
 * @author halle
 */
public class MoradorDTO {
    
    private String nome;
    private String cpf;
    private String telefone;
    private String residencia;
    private String sexo;
    private String estadoCivil;

    public MoradorDTO(String nome, String cpf, String telefone, String residencia, String sexo, String estadoCivil) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.residencia = residencia;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
    }

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
    
    
}
