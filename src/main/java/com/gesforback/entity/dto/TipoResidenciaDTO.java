
package com.gesforback.entity.dto;

import lombok.Data;

@Data
public class TipoResidenciaDTO {
    
    private String id;
    private String text;

    public TipoResidenciaDTO(String id,String text) {
        this.id = id;
        this.text = text;
    }
    
}
