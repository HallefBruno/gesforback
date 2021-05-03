
package com.gesforback.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Filipeta implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;
    
    @Column(length = 100, nullable = false)
    @Size(max = 100, min = 3, message = "Quantidade máxima de caracter 100 e minimo 3")
    @NotBlank(message = "Numero não pode ser espaços em branco!")
    @NotEmpty(message = "Numero não pode ser vazio!")
    @NotNull(message = "Numero não pode ser null!")
    private String numero;
    
    @JoinColumn(nullable = false)
    @ManyToOne
    private Portaria portaria;
    
    @PrePersist
    private void removeLastSpaceBlankPersist() {
        this.numero = StringUtils.strip(this.numero);
    }
    
    @PreUpdate
    private void removeLastSpaceBlankUpdate() {
        this.numero = StringUtils.strip(this.numero);
    }
    
}
