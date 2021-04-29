
package com.gesforback.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

@Entity
public class Morador implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;
    
    //@Size(max = 100, min = 3, message = "Quantidade máxima de caracter 12 e minimo 10")
    @NotBlank(message = "Nome não pode ter espaços em branco!")
    @NotEmpty(message = "Nome não pode ser vazio!")
    @NotNull(message = "Nome não pode ser null!")
    @Column(length = 100, nullable = false)
    private String nome;
    
    //@Size(max = 14, min = 14, message = "Quantidade máxima de caracter 11")
    @NotBlank(message = "CPF não pode ter espaços em branco!")
    @NotEmpty(message = "CPF não pode ser vazio!")
    @NotNull(message = "CPF não pode ser null!")
    @Column(unique = true, length = 11, nullable = false)
    private String cpf;
    
    //@Size(max = 11, message = "Quantidade máxima de caracter 11")
    @NotBlank(message = "RG não pode ter espaços em branco!")
    @NotEmpty(message = "RG não pode ser vazio!")
    @NotNull(message = "RG não pode ser null!")
    @Column(unique = true, length = 11,nullable = false)
    private String rg;
    
    //@Size(max = 11, min = 3, message = "Quantidade máxima de caracter 11 e minimo 3")
    @NotBlank(message = "Orgão emissor não pode ter espaços em branco!")
    @NotEmpty(message = "Orgão emissor não pode ser vazio!")
    @NotNull(message = "Orgão emissor não pode ser null!")
    @Column(length = 11, nullable = false, name = "orgao_emissor")
    private String orgaoEmissor;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento", nullable = false)
    Date dataNascimento;
    
    //@Size(max = 100, min = 3, message = "Quantidade máxima de caracter 100 e minimo 3")
    @NotBlank(message = "Naturalidade não pode ter espaços em branco!")
    @NotEmpty(message = "Naturalidade não pode ser vazio!")
    @NotNull(message = "Naturalidade não pode ser null!")
    @Column(length = 100, nullable = false)
    private String naturalidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, name = "estado_civil")
    private EstadoCivil estadoCivil;
    
    //@Size(max = 20, message = "Quantidade máxima de caracter 20")
    @NotBlank(message = "Sexo não pode ter espaços em branco!")
    @NotEmpty(message = "Sexo não pode ser vazio!")
    @NotNull(message = "Sexo não pode ser null!")
    @Column(nullable = false, length = 20)
    private String sexo;
    
    //@Size(max = 255, message = "Quantidade máxima de caracter 255")
    @NotBlank(message = "Residência não pode ter espaços em branco!")
    @NotEmpty(message = "Residência não pode ser vazio!")
    @NotNull(message = "Residência não pode ser null!")
    @Column(nullable = false, length = 255, unique = true)
    private String residencia;
    
    @NotNull(message = "Quantidade de moradores não pode ser null!")
    @Column(nullable = false, name = "qtd_moradores")
    private Integer qdtMoradores;
    
    @NotNull(message = "Animal domestico não pode ser null!")
    @Column(nullable = false, name = "animal_domestico")
    private Boolean animalDomestico;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40, name = "tipo_moradia")
    private TipoResidencia tipoMoradia;

    @OneToMany(mappedBy = "morador", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Telefone> telefones;
    
    @OneToMany(mappedBy = "morador", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<MoradorAutomovel> automoveis;
    
    @OneToMany(mappedBy = "morador", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<MoradorSecundario> moradorSecundarios;
    
    @PrePersist
    @PreUpdate
    @PostPersist
    @PostUpdate
    private void removeLastSpaceBlankPersist() {
        this.nome = StringUtils.strip(this.nome);
        this.cpf = StringUtils.strip(this.cpf);
        this.cpf = this.cpf.replaceAll("[^\\w\\s]", "");
        this.rg = StringUtils.strip(this.rg);
        this.orgaoEmissor = StringUtils.strip(this.orgaoEmissor);
        this.naturalidade = StringUtils.strip(this.naturalidade);
        this.sexo = StringUtils.strip(this.sexo);
        this.residencia = StringUtils.strip(this.residencia);
    }

    //@NumberFormat(style = Style.CURRENCY, pattern = "###,##0.00")
    //@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss", iso = ISO.DATE_TIME)


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

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public Integer getQdtMoradores() {
        return qdtMoradores;
    }

    public void setQdtMoradores(Integer qdtMoradores) {
        this.qdtMoradores = qdtMoradores;
    }

    public Boolean getAnimalDomestico() {
        return animalDomestico;
    }

    public void setAnimalDomestico(Boolean animalDomestico) {
        this.animalDomestico = animalDomestico;
    }

    public TipoResidencia getTipoMoradia() {
        return tipoMoradia;
    }

    public void setTipoMoradia(TipoResidencia tipoMoradia) {
        this.tipoMoradia = tipoMoradia;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
    }

    public Set<MoradorAutomovel> getAutomoveis() {
        return automoveis;
    }

    public void setAutomoveis(Set<MoradorAutomovel> automoveis) {
        this.automoveis = automoveis;
    }

    public Set<MoradorSecundario> getMoradorSecundarios() {
        return moradorSecundarios;
    }

    public void setMoradorSecundarios(Set<MoradorSecundario> moradorSecundarios) {
        this.moradorSecundarios = moradorSecundarios;
    }
    
    
    
}
