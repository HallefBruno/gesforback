
package com.gesforback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "morador_automovel")
public class MoradorAutomovel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "morador_id")
    @JsonBackReference
    private Morador morador;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "automovel_id")
    private Automovel automovel;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "morador_secundario_id")
    private MoradorSecundario moradorSecundario;
    
    @NotBlank(message = "Placa não pode ter espaços em branco!")
    @NotEmpty(message = "Placa não pode ser vazio!")
    @NotNull(message = "Placa não pode ser null!")
    @Column(name = "placa", unique = true, nullable = false, length = 7)
    private String placa;
    
    @NotBlank(message = "Cor não pode ter espaços em branco!")
    @NotEmpty(message = "Cor não pode ser vazio!")
    @NotNull(message = "Cor não pode ser null!")
    @Size(max = 25, message = "Quantidade máxima de caracter 25")
    @Column(name = "cor", nullable = false, length = 25)
    private String cor;
    
    @PrePersist
    @PreUpdate
    @PostPersist
    @PostUpdate
    private void removeLastSpaceBlankPersist() {
        this.placa = StringUtils.strip(this.placa);
        this.cor = StringUtils.strip(this.cor);
        this.placa = this.placa.replaceAll("[^\\w\\s]", "");
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }

    public MoradorSecundario getMoradorSecundario() {
        return moradorSecundario;
    }

    public void setMoradorSecundario(MoradorSecundario moradorSecundario) {
        this.moradorSecundario = moradorSecundario;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
