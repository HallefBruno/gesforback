
package com.gesforback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

@Entity
public class MoradorSecundario implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;
    
    @NotBlank(message = "Nome não pode ter espaços em branco!")
    @NotEmpty(message = "Nome não pode ser vazio!")
    @NotNull(message = "Nome não pode ser null!")
    @Column(length = 100, nullable = false)
    private String nome;
    
    @NotBlank(message = "CPF não pode ter espaços em branco!")
    @NotEmpty(message = "CPF não pode ser vazio!")
    @NotNull(message = "CPF não pode ser null!")
    @Column(unique = true, length = 11, nullable = false)
    private String cpf;
    
    @NotBlank(message = "RG não pode ter espaços em branco!")
    @NotEmpty(message = "RG não pode ser vazio!")
    @NotNull(message = "RG não pode ser null!")
    @Column(unique = true, length = 11,nullable = false)
    private String rg;
    
    @NotBlank(message = "Orgão emissor não pode ter espaços em branco!")
    @NotEmpty(message = "Orgão emissor não pode ser vazio!")
    @NotNull(message = "Orgão emissor não pode ser null!")
    @Column(length = 11, nullable = false, name = "orgao_emissor")
    private String orgaoEmissor;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento", nullable = false)
    private Date dataNascimento;
    
    @NotBlank(message = "Naturalidade não pode ter espaços em branco!")
    @NotEmpty(message = "Naturalidade não pode ser vazio!")
    @NotNull(message = "Naturalidade não pode ser null!")
    @Column(length = 100, nullable = false)
    private String naturalidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, name = "estado_civil")
    private EstadoCivil estadoCivil;
    
    @NotBlank(message = "Sexo não pode ter espaços em branco!")
    @NotEmpty(message = "Sexo não pode ser vazio!")
    @NotNull(message = "Sexo não pode ser null!")
    @Column(nullable = false, length = 20)
    private String sexo;
    
    @NotBlank(message = "Número não pode ser espaços em branco!")
    @NotEmpty(message = "Número não pode ser vazio!")
    @NotNull(message = "Número não pode ser null!")
    @Column(length = 11, nullable = false, unique = true)
    private String telefone;
    
    @NotBlank(message = "Residência não pode ter espaços em branco!")
    @NotEmpty(message = "Residência não pode ser vazio!")
    @NotNull(message = "Residência não pode ser null!")
    @Column(nullable = false, length = 255)
    private String residencia;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "morador_secundario_id"),inverseJoinColumns = @JoinColumn(name = "automovel_morador_id"))
    private Set<AutomovelMorador> automoveisMoradores;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "morador_id")
    @JsonBackReference
    private Morador morador;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "grau_parentesco", nullable = false, length = 50)
    private GrauParentesco grauParentesco;
    
    @PrePersist
    @PreUpdate
    @SuppressWarnings("null")
    private void prePersistPreUpdate() {
        this.nome = StringUtils.strip(this.nome);
        this.cpf = StringUtils.getDigits(this.cpf);
        this.rg = StringUtils.strip(this.rg);
        this.orgaoEmissor = StringUtils.strip(this.orgaoEmissor);
        this.naturalidade = StringUtils.strip(this.naturalidade);
        this.telefone = StringUtils.getDigits(this.telefone);
        if(this.residencia == null || StringUtils.isBlank(this.residencia)) {
            this.residencia = this.morador.getResidencia();
        }
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

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public Set<AutomovelMorador> getAutomoveisMoradores() {
        return automoveisMoradores;
    }

    public void setAutomoveisMoradores(Set<AutomovelMorador> automoveisMoradores) {
        this.automoveisMoradores = automoveisMoradores;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

    public GrauParentesco getGrauParentesco() {
        return grauParentesco;
    }

    public void setGrauParentesco(GrauParentesco grauParentesco) {
        this.grauParentesco = grauParentesco;
    }

    

    
}
//    @OneToMany(mappedBy = "moradorSecundario", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<AutomovelMorador> automoveis;