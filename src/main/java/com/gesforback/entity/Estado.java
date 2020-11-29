package com.gesforback.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sud
 */
@Entity
@Data
public class Estado implements Serializable {
    
    @Id
    private UUID Id;
    
    @Column(length = 100, nullable = false, unique = true)
    @Size(max = 100, min = 3, message = "Quantidade máxima de caracter 100 e minimo 3")
    @NotBlank(message = "Nome não pode ser espaçõs em branco!")
    @NotEmpty(message = "Nome não pode ser vazio!")
    @NotNull(message = "Nome não pode ser null!")
    private String nome;
    
    
    @Column(length = 2, nullable = false, unique = true)
    @Size(max = 2, min = 2, message = "Quantidade máxima de caracter 2 e minimo 2")
    @NotBlank(message = "UF não pode ser espaçõs em branco!")
    @NotEmpty(message = "UF não pode ser vazio!")
    @NotNull(message = "UF não pode ser null!")
    private String uf;
    
    @PrePersist
    public void removeLastSpaceBlankPersist() {
        this.nome = StringUtils.strip(this.nome);
        this.uf = StringUtils.strip(this.uf);
    }
    
    @PreUpdate
    public void removeLastSpaceBlankUpdate() {
        this.nome = StringUtils.strip(this.nome);
        this.uf = StringUtils.strip(this.uf);
    }
    
}
