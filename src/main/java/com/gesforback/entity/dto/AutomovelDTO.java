
package com.gesforback.entity.dto;

import lombok.Data;

@Data
public class AutomovelDTO {
    
    private String id;
    private String text;
    private String tipo;

    public AutomovelDTO(String id, String text, String tipo) {
        this.id = id;
        this.text = text;
        this.tipo = tipo;
    }

    public AutomovelDTO() {
    }
    
    

}
