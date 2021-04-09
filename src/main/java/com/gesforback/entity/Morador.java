
package com.gesforback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Entity @Data
public class Morador implements Serializable {
    
    @Id
    private UUID id;
    
    @Size(max = 100, min = 3, message = "Quantidade máxima de caracter 12 e minimo 10")
    @NotBlank(message = "Nome não pode ter espaços em branco!")
    @NotEmpty(message = "Nome não pode ser vazio!")
    @NotNull(message = "Nome não pode ser null!")
    @Column(length = 100, nullable = false, unique = true)
    private String nome;
    
    @Size(max = 11, min = 11, message = "Quantidade máxima de caracter 11")
    @NotBlank(message = "CPF não pode ter espaços em branco!")
    @NotEmpty(message = "CPF não pode ser vazio!")
    @NotNull(message = "CPF não pode ser null!")
    @Column(unique = true, length = 11, nullable = false)
    private String cpf;
    
    @Size(max = 11, message = "Quantidade máxima de caracter 11")
    @NotBlank(message = "RG não pode ter espaços em branco!")
    @NotEmpty(message = "RG não pode ser vazio!")
    @NotNull(message = "RG não pode ser null!")
    @Column(unique = true, length = 11,nullable = false)
    private String rg;
    
    @Size(max = 11, min = 3, message = "Quantidade máxima de caracter 11 e minimo 3")
    @NotBlank(message = "Orgão emissor não pode ter espaços em branco!")
    @NotEmpty(message = "Orgão emissor não pode ser vazio!")
    @NotNull(message = "Orgão emissor não pode ser null!")
    @Column(length = 11, nullable = false, name = "orgao_emissor")
    private String orgaoEmissor;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento", nullable = false)
    Date dataNascimento;
    
    @Size(max = 100, min = 3, message = "Quantidade máxima de caracter 100 e minimo 3")
    @NotBlank(message = "Naturalidade não pode ter espaços em branco!")
    @NotEmpty(message = "Naturalidade não pode ser vazio!")
    @NotNull(message = "Naturalidade não pode ser null!")
    @Column(length = 100, nullable = false)
    private String naturalidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, name = "estado_civil")
    private EstadoCivil estadoCivil;
    
    @Size(max = 20, message = "Quantidade máxima de caracter 20")
    @NotBlank(message = "Sexo não pode ter espaços em branco!")
    @NotEmpty(message = "Sexo não pode ser vazio!")
    @NotNull(message = "Sexo não pode ser null!")
    @Column(nullable = false, length = 20)
    private String sexo;
    
    @Size(max = 255, message = "Quantidade máxima de caracter 255")
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
    
    @OneToMany(mappedBy = "morador")
    private List<Telefone> telefones;
    
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinTable(name = "automoveis_morador", joinColumns = @JoinColumn(name = "morador_id"), inverseJoinColumns = @JoinColumn(name = "automovel_id"))
    //private List<Automovel> automoveis;

    @PrePersist
    private void removeLastSpaceBlankPersist() {
        this.nome = StringUtils.strip(this.nome);
        this.cpf = StringUtils.strip(this.cpf);
        this.rg = StringUtils.strip(this.rg);
        this.orgaoEmissor = StringUtils.strip(this.orgaoEmissor);
        this.naturalidade = StringUtils.strip(this.naturalidade);
        this.sexo = StringUtils.strip(this.sexo);
        this.residencia = StringUtils.strip(this.residencia);
    }
    
    @PreUpdate
    private void removeLastSpaceBlankUpdate() {
        this.nome = StringUtils.strip(this.nome);
        this.cpf = StringUtils.strip(this.cpf);
        this.rg = StringUtils.strip(this.rg);
        this.orgaoEmissor = StringUtils.strip(this.orgaoEmissor);
        this.naturalidade = StringUtils.strip(this.naturalidade);
        this.sexo = StringUtils.strip(this.sexo);
        this.residencia = StringUtils.strip(this.residencia);
    }
    
}
