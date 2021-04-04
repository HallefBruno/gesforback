
package com.gesforback.controller;

import com.gesforback.entity.EstadoCivil;
import com.gesforback.entity.Fabricante;
import com.gesforback.entity.TipoResidencia;
import com.gesforback.entity.dto.FabricanteDTO;
import com.gesforback.entity.dto.SexoDTO;
import com.gesforback.entity.dto.TipoResidenciaDTO;
import com.gesforback.service.FabricanteService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"morador"})
public class MoradorController {
    
    @Autowired
    private FabricanteService fabricanteService;
    
    @GetMapping("estado-civil")
    public ResponseEntity<List<SexoDTO>> estadosCivil() {
        List<SexoDTO> estadoCivil = new ArrayList<>();
        for (EstadoCivil civil : EstadoCivil.values()) {
            estadoCivil.add(new SexoDTO(civil.name(), civil.getDescricao()));
        }
        return new ResponseEntity<>(estadoCivil, HttpStatus.OK);
    }
    
    @GetMapping("tipo-residencia")
    public ResponseEntity<List<TipoResidenciaDTO>> tipoMoradia() {
        List<TipoResidenciaDTO> tiposResidencias = new ArrayList<>();
        for (TipoResidencia tipoMoradia : TipoResidencia.values()) {
            tiposResidencias.add(new TipoResidenciaDTO(tipoMoradia.name(), tipoMoradia.getDescricao()));
        }
        return new ResponseEntity<>(tiposResidencias, HttpStatus.OK);
    }
    
    @GetMapping("fabricantes")
    public List<FabricanteDTO> fabricantes() {
        List<FabricanteDTO> fabricantes = new ArrayList<>();
        fabricanteService.fabricantes().forEach((fabricante) -> {
            fabricantes.add(new FabricanteDTO(fabricante.getId().toString(), fabricante.getNome()));
        });
        return fabricantes;
    }
    
}
