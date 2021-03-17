
package com.gesforback.entity;

import lombok.Data;

@Data
public class SexoDTO {
    
    private String id;
    private String text;

    public SexoDTO(String id,String text) {
        this.id = id;
        this.text = text;
    }

}
