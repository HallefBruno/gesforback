
package com.gesforback.entity.dto;

import java.util.UUID;

/**
 *
 * @author halle
 */
public class MoradorDTO {
    
    private UUID id;
    private String nome;
    private String cpf;
    private String telefone;
    private String residencia;
    private String sexo;
    private String estadoCivil;
    private boolean isProprietario;

    public MoradorDTO() {
    }

    public MoradorDTO(UUID id, String nome, String cpf, String telefone, String residencia, String sexo, String estadoCivil, boolean isProprietario) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.residencia = residencia;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        this.isProprietario = isProprietario;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean isIsProprietario() {
        return isProprietario;
    }

    public void setIsProprietario(boolean isProprietario) {
        this.isProprietario = isProprietario;
    }

    
}
