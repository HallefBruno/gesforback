
package com.gesforback.entity.dto;

import java.util.List;
import lombok.Data;

@Data
public class ResultSelectAutomoveis {
    
    private Long totalItens;
    private List<AutomovelDTO> items;
    
}
