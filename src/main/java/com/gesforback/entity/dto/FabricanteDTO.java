
package com.gesforback.entity.dto;

@lombok.Data
public class FabricanteDTO {
    
    private String id;
    private String text;

    public FabricanteDTO(String id, String text) {
        this.id = id;
        this.text = text;
    }
    
}
