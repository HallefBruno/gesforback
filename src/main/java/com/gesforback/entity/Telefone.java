
package com.gesforback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Entity
@Data
public class Telefone implements Serializable {
    
    @Id
    private UUID id;
    
    @Column(length = 100, nullable = false, unique = true)
    @Size(max = 12, min = 10, message = "Quantidade máxima de caracter 12 e minimo 10")
    @NotBlank(message = "Número não pode ser espaços em branco!")
    @NotEmpty(message = "Número não pode ser vazio!")
    @NotNull(message = "Número não pode ser null!")
    private String numero;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="morador_id") 
    @JsonIgnore
    Morador morador;
    
    @PrePersist
    private void removeLastSpaceBlankPersist() {
        this.numero = StringUtils.strip(this.numero);
    }
    
    @PreUpdate
    private void removeLastSpaceBlankUpdate() {
        this.numero = StringUtils.strip(this.numero);
    }
}
