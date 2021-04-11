
package com.gesforback.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Automovel implements Serializable {
    
    @Id
    private Long id;
    
    @Column(length = 100, nullable = false)
    @Size(max = 100, min = 3, message = "Quantidade máxima de caracter 12 e minimo 10")
    @NotBlank(message = "Nome não pode ter espaços em branco!")
    @NotEmpty(message = "Nome não pode ser vazio!")
    @NotNull(message = "Nome não pode ser null!")
    private String nome;
    
    
    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;
    
    @Column(length = 100, nullable = false, name = "tipo_automovel")
    @Size(max = 100, min = 3, message = "Quantidade máxima de caracter 12 e minimo 10")
    @NotBlank(message = "Tipo automóvel não pode ter espaços em branco!")
    @NotEmpty(message = "Tipo automóvel não pode ser vazio!")
    @NotNull(message = "Tipo automóvel não pode ser null!")
    private String tipoAutomovel;
    
    @PrePersist
    private void removeLastSpaceBlankPersist() {
        this.nome = StringUtils.strip(this.nome);
    }
    
    @PreUpdate
    private void removeLastSpaceBlankUpdate() {
        this.nome = StringUtils.strip(this.nome);
    }
    
}
