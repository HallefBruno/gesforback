
package com.gesforback.controller;

import com.gesforback.entity.EstadoCivil;
import com.gesforback.entity.SexoDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"morador"})
public class MoradorController {
    
    @GetMapping("estado-civil")
    public ResponseEntity<List<SexoDTO>> estadosCivil() {
        List<SexoDTO> estadoCivil = new ArrayList<>();
        for (EstadoCivil civil : EstadoCivil.values()) {
            estadoCivil.add(new SexoDTO(civil.name(), civil.getDescricao()));
        }
        return new ResponseEntity<>(estadoCivil, HttpStatus.OK);
    }
    
}
